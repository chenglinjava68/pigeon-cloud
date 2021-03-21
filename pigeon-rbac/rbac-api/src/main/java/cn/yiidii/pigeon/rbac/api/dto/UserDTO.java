package cn.yiidii.pigeon.rbac.api.dto;

import cn.yiidii.pigeon.rbac.api.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:17
 */
@Data
@ApiModel("用户DTO")
public class UserDTO extends User {

    @ApiModelProperty(value = "确认密码")
    @NotBlank(message = "确认密码不能为空",groups = {Update.class})
    private String confirmPassword;

    @ApiModelProperty(value = "角色")
    private List<RoleDTO> roles;

    @ApiModelProperty(value = "菜单")
    private List<MenuDTO> menus;

    @ApiModelProperty(value = "权限")
    private List<PermissionDTO> permissions;

}
