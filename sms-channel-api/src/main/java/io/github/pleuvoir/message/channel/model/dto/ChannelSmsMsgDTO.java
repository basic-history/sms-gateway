package io.github.pleuvoir.message.channel.model.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import io.github.pleuvoir.message.channel.common.ToJSON;
import lombok.Data;

/**
 * 通道短信验证码DTO
 */
@Data
public class ChannelSmsMsgDTO implements Serializable, ToJSON {

	private static final long serialVersionUID = 8783884675768405L;

	@NotBlank(message="手机号不能为空")
	@Pattern(regexp="^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$", message="手机号格式有误")
	private String phone;				// 接收短信的手机号
	
	@NotBlank(message="短信内容不能为空")
	private String content;				//短信内容
	
	@NotBlank(message="通道编号")
	private String channelCode;			//通道编号
	
	private String signCode; 			//通道短信编号
	
	private String signName; 			//通道短信签名
	
	private String templateCode; 		//通道短信模板编号
	
	private String templateName; 		//通道短信模板名称
	
	private String templateParam; 		//短信模板参数
	
	@NotBlank(message="访问秘钥ID")
	private String accessKeyId; 		//访问秘钥ID
	
	@NotBlank(message="访问秘钥")
	private String accessKeySecret; 	//访问秘钥
	
	@NotBlank(message="发送短信接口地址")
	private String sendMsgUrl;			//发送短信接口地址
	
	private String extendParam;			//扩展参数
	
	private Date schtime;				//定时发送时间
	
}
