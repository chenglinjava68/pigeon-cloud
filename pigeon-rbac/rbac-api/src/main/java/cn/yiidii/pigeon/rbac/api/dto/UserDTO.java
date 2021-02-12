package cn.yiidii.pigeon.rbac.api.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:17
 */
@Data
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private List<RoleDTO> roles;
    private List<ResourceDTO> resources;

}
