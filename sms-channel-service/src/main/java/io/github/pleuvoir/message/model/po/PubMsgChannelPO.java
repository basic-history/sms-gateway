package io.github.pleuvoir.message.model.po;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("pub_msg_channel")
public class PubMsgChannelPO implements Serializable {

    private static final long serialVersionUID = 5368611470869764507L;

    public static final  String STATUS_YES = "1";

    public static final  String STATUS_NO = "0";

    @TableId("id")
    private String id;

    @TableField("code")
    private String code;    // 通道编号

    @TableField("name")
    private String name;    // 通道名称

    @TableField("status")
    private String status;  // 状态 1可用，0不可用
}
