package io.github.pleuvoir;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;

public class DubboTraceFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(DubboTraceFilter.class);

	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		try {
			FilterDesc filterReq = new FilterDesc();
			filterReq.setInterfaceName(invocation.getInvoker().getInterface().getName());
			filterReq.setMethodName(invocation.getMethodName());
			filterReq.setArgs(invocation.getArguments());
			logger.debug("dubbo 请求数据:" + JSON.toJSONString(filterReq));
			Result result = invoker.invoke(invocation);
			if (result.hasException() && invoker.getInterface() != GenericService.class) {
				logger.warn("dubbo 执行异常", result.getException());
			} else {
				FilterDesc filterRsp = new FilterDesc();
				filterRsp.setMethodName(invocation.getMethodName());
				filterRsp.setInterfaceName(invocation.getInvoker().getInterface().getName());
				filterRsp.setArgs(new Object[] { result.getValue() });
				logger.debug("dubbo 执行成功，返回数据" + JSON.toJSONString(filterRsp));
			}
			return result;
		} catch (RuntimeException e) {
			logger.error("dubbo 未知异常" + RpcContext.getContext().getRemoteHost() + ". service: "
					+ invoker.getInterface().getName() + ", method: " + invocation.getMethodName() + ", exception: "
					+ e.getClass().getName() + ": " + e.getMessage(), e);
			throw e;
		}
	}

	protected static class FilterDesc {
		// 接口名
		private String interfaceName;
		// 方法名
		private String methodName;
		// 参数
		private Object[] args;
		
		// getter and setter
		public String getInterfaceName() {
			return interfaceName;
		}
		public String getMethodName() {
			return methodName;
		}
		public Object[] getArgs() {
			return args;
		}
		public void setInterfaceName(String interfaceName) {
			this.interfaceName = interfaceName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public void setArgs(Object[] args) {
			this.args = args;
		}
	}
}
