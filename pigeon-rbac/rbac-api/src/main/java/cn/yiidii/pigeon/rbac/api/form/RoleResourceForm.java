package cn.yiidii.pigeon.rbac.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色菜单权限form
 *
 * @author: YiiDii Wang
 * @create: 2021-03-18 18:24
 */
@Data
@ApiModel("角色菜单权限Form")
public class RoleResourceForm {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    Long roleId;

    @NotEmpty(message = "菜单ID不能为空")
    List<Long> menuIdList;
}
