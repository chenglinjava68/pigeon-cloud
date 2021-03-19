package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 角色
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 18:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("role")
@ApiModel(value = "Role", description = "角色")
public class Role extends Entity<Long> {

    @ApiModelProperty(value = "用户编码")
    @NotEmpty(message = "用户编码不能为空")
    @TableField(value="code")
    @Pattern(regexp = "\\w{3,20}", message = "角色编码限制3-20位，数字字母及下划线")
    private String code;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    @Length(max = 30, message = "名称长度不能超过30")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "描述")
    @NotEmpty(message = "描述不能为空")
    @TableField(value = "`desc`")
    private String desc;

}
