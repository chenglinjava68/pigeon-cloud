package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * 资源
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:10
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("resource")
@ApiModel(value = "Resource", description = "资源")
@AllArgsConstructor
public class Resource extends Entity<Long> {

    @ApiModelProperty(value = "资源code")
    @NotEmpty(message = "资源code不能为空")
    @TableField(value = "code")
    private String code;

}
