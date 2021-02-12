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
 * 用户角色
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 19:09
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_role")
@ApiModel(value = "UserRole", description = "用户角色")
@AllArgsConstructor
public class UserRole extends SuperEntity<Long> {

    @ApiModelProperty(value = "用户ID")
    @NotEmpty(message = "用户ID不能为空")
    @TableField(value = "user_id")
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    @NotEmpty(message = "角色ID不能为空")
    @TableField(value = "role_id")
    private Integer roleId;

}
