package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * 角色资源
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:31
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role_resource")
@ApiModel(value = "RoleResource", description = "角色资源")
@AllArgsConstructor
public class RoleResource extends SuperEntity<Long> {

    @ApiModelProperty(value = "角色ID")
    @NotEmpty(message = "角色ID不能为空")
    @TableField(value = "role_id")
    private Long roleId;

    @ApiModelProperty(value = "资源ID")
    @NotEmpty(message = "资源ID不能为空")
    @TableField(value = "resource_id")
    private Long resourceId;

    @ApiModelProperty(value = "资源类型")
    @NotEmpty(message = "资源类型不能为空")
    @TableField(value = "resource_id")
    private Integer type;

}
