package cn.yiidii.pigeon.rbac.api.dto;

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
public class UserDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "角色")
    private List<RoleDTO> roles;

    @ApiModelProperty(value = "资源")
    private List<ResourceDTO> resources;

}
