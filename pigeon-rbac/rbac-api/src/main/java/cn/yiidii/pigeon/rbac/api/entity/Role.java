package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 角色
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 18:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role")
@ApiModel(value = "Role", description = "角色")
public class Role extends Entity<Long> {

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 30, message = "名称长度不能超过30")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    @TableField(value = "desc")
    private String desc;

    private Integer state;
}
