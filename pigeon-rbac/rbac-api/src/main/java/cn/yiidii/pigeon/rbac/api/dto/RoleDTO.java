package cn.yiidii.pigeon.rbac.api.dto;

import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:37
 */
@Data
@ApiModel("角色DTO")
public class RoleDTO implements Serializable {

    @ApiModelProperty(value = "用户编码")
    @NotEmpty(message = "用户编码不能为空")
    @Pattern(regexp = "\\w{3,20}", message = "角色编码限制3-20位，数字字母及下划线")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 30, message = "名称长度不能超过30")
    private String name;

    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    private String desc;

    @ApiModelProperty(value = "状态")
    protected Status status;

}
