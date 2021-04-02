package cn.yiidii.pigeon.rbac.api.form;

import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-18 10:39
 */
@Data
@ApiModel("角色Form")
public class RoleForm {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空",groups = SuperEntity.Update.class)
    private Long id;

    @ApiModelProperty(value = "组织ID")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "角色编码")
    @NotEmpty(message = "角色编码不能为空")
    @Pattern(regexp = "\\w{3,20}", message = "角色编码限制3-20位，数字字母及下划线")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 30, message = "名称长度不能超过30")
    private String name;

    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    private String desc;

}
