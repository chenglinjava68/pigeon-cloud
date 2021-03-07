package cn.yiidii.pigeon.openapi.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 中国联通登录Form类
 * </p>
 *
 * @author: YiiDii Wang
 * @create: 2020-12-02 21:20
 */
@Data
@ApiModel("运营商登陆表单")
public class TelecomLoginForm {

    /**
     * 运营商类型
     */
    @NotNull(message = "type:请选择运营商")
    @Range(min = 1, max = 3, message = "请选择运营商")
    @ApiModelProperty(value = "运营商类型")
    private Integer type;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    @Pattern(regexp = "\\d{11}", message = "手机号码格式不正确")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * 验证码
     */
    @Pattern(regexp = "\\d{4,6}", message = "验证码格式不正确")
    @ApiModelProperty(value = "验证码")
    private String password;

    /**
     * 图片验证码
     */
    @ApiModelProperty(value = "图形验证码")
    private String userContent;

    /**
     * 图片验证码的imageId
     */
    @ApiModelProperty(value = "图片验证码的ID")
    private String imageId;

}
