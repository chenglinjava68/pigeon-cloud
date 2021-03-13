package cn.yiidii.pigeon.demo.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-12 17:56
 */
@Data
@ApiModel(value = "EmailDTO", description = "邮件传输对象")
@RequiredArgsConstructor
//@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO implements Serializable {

    private static final long serialVersionUID = -6367776752658047039L;

    @ApiModelProperty(value = "主题")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "收件人")
    private String[] toWho;

    @ApiModelProperty(value = "抄送人")
    private String[] ccPeoples;

    @ApiModelProperty(value = "密送人")
    private String[] bccPeoples;

    @ApiModelProperty(value = "附件")
    private String[] attachments;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "模板名称")
    private String templateName;

    @ApiModelProperty(value = "模板Bean")
    private Object model;

}
