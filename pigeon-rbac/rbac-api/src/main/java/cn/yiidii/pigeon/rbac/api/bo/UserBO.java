package cn.yiidii.pigeon.rbac.api.bo;

import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import cn.yiidii.pigeon.rbac.api.dto.RoleDTO;
import cn.yiidii.pigeon.rbac.api.entity.RoleResource;
import cn.yiidii.pigeon.rbac.api.enumeration.Sex;
import cn.yiidii.pigeon.rbac.api.enumeration.UserSource;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:17
 */
@Data
public class UserBO {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String mobile;

    private Sex sex;

    private String avatar;

    private String desc;

    private Integer pwdErrTimes;

    private LocalDateTime lastPwdErrTime;

    private LocalDateTime lastLoginTime;

    private Boolean isDefault;

    private Status status;

    private UserSource source;

    private String uuid;

    /**
     * 角色
     */
    private List<RoleDTO> roles;

    /**
     * 资源
     */
    private List<ResourceBO> resources;

}
