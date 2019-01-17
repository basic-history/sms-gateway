package io.github.pleuvoir.message.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;

@Data
@TableName("pub_msg_channel")
public class PubMsgChannelPO implements Serializable {

	private static final long serialVersionUID = 5368611470869764507L;

	@TableId("id")
	private String id;

	@TableField("code")
	private String code; // 通道编号
	
	@TableField("name")
	private String name; // 通道名称
	
}
