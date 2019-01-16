package io.github.pleuvoir.message.channel.jiguang;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import io.github.pleuvoir.message.channel.BaseChannelService;
import io.github.pleuvoir.message.channel.ChannelService;
import io.github.pleuvoir.message.enums.ChannelEnum;
import io.github.pleuvoir.message.factory.ServiceChannel;
import io.github.pleuvoir.message.model.dto.ChannelResultDTO;
import io.github.pleuvoir.message.model.dto.ChannelSmsMsgDTO;
import io.github.pleuvoir.message.util.Base64;
import io.github.pleuvoir.message.util.net.ResponseObj;
import io.github.pleuvoir.message.util.net.SyncHttpClient;

@ServiceChannel(ChannelEnum.JIGUANG)
public class JiguangChannelService extends BaseChannelService implements ChannelService {

	@Autowired
	private SyncHttpClient syncHttpClient;
	
	@Override
	public ChannelResultDTO sendSmsCode(ChannelSmsMsgDTO channelSmsDTO) {
		logger.info("【极光】发送短信验证码，请求通道入参：{}", channelSmsDTO.toJSON());

		ChannelResultDTO result = new ChannelResultDTO();
		
		Map<String, String> customHeader = new HashMap<String, String>();
		String authString = channelSmsDTO.getAccessKeyId() + ":" + channelSmsDTO.getAccessKeySecret();
		String base64AuthString = Base64.encode(authString.getBytes());
		customHeader.put("Authorization", "Basic " + base64AuthString);

		JSONObject param = new JSONObject();
		param.put("mobile", channelSmsDTO.getPhone());
		param.put("temp_id", channelSmsDTO.getTemplateCode());
		
		JSONObject tempParam = new JSONObject();
		tempParam.put(channelSmsDTO.getTemplateParam(), channelSmsDTO.getContent());
		param.put("temp_para", tempParam);
		
		ResponseObj responseObj = null;
		try {
			responseObj = syncHttpClient.sendPost(channelSmsDTO.getSendMsgUrl(), customHeader, param.toString(), Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.error("{}，异常：", generateSendErrorMessage(channelSmsDTO), e);
			result.setCode(ChannelResultDTO.ERROR_CODE);
			result.setMsg("短信发送请求失败");
			return result;
		}
		
		logger.info("【极光】发送短信验证码，接受到响应：{}", responseObj);

		return result;
	}
	
}
