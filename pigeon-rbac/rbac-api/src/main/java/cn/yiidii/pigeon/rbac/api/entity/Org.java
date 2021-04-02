package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * 组织
 *
 * @author: YiiDii Wang
 * @create: 2021-04-02 12:57
 */
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("org")
@ApiModel(value = "Org", description = "组织")
@AllArgsConstructor
public class Org extends TreeEntity<Org, Long> {

    @ApiModelProperty(value = "组织描述")
    @TableField(value = "`desc`")
    private String desc;

}
