package cn.yiidii.pigeon.rbac.api.dto;

import lombok.Data;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:37
 */
@Data
public class RoleDTO {

    private Long id;
    private String name;
    private String desc;
    private Integer state;

}
