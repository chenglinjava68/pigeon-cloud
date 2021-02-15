package cn.yiidii.pigeon.rbac.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:37
 */
@Data
@ApiModel("角色DTO")
public class RoleDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色描述")
    private String desc;

    @ApiModelProperty(value = "角色状态")
    private Integer state;

}
