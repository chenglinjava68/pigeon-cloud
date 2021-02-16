package cn.yiidii.pigeon.rbac.api.dto;

import cn.yiidii.pigeon.rbac.api.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:17
 */
@Data
@ApiModel("用户DTO")
public class UserDTO extends User {

    @ApiModelProperty(value = "角色")
    private List<RoleDTO> roles;

    @ApiModelProperty(value = "资源")
    private List<ResourceDTO> resources;

    private UserDTO transUserToUserDTO() {

        return null;
    }

}
