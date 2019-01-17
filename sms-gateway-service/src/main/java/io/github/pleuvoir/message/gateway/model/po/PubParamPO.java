package io.github.pleuvoir.message.gateway.model.po;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("pub_param")
public class PubParamPO implements Serializable {

	private static final long serialVersionUID = -8866298380650761859L;

	@TableId("code")
	private String code;			//参数编号
	
    @TableField("name")
	private String name;			//参数名称
	
    @TableField("group_code")
	private String groupCode;  		//分组名
	
    @TableField("decimal_val")
	private BigDecimal decimalVal;	//decimal类型
	
    @TableField("int_val")
	private Integer intVal;			//int类型
	
    @TableField("str_val")
	private String strVal;			//字符串类型
	
    @TableField("boolean_val")
	private Boolean booleanVal;     //布尔类型
    
    @TableField("type")
	private String type;			//参数类型	1：decimal 2：int 3：string 4：boolean
	
    @TableField("modify_flag")
	private String modifyFlag;		//是否可修改		1：允许；0：不允许
	
    @TableField("remark")
	private String remark;			//描述

	/** 短信缓存时间(单位分钟)(接口) */
	public static final String CODE_SMS_CACAHE_TIME = "1003";
	
}
