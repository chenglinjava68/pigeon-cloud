package cn.yiidii.pigeon.demo.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 测试entity
 *
 * @author: YiiDii Wang
 * @create: 2021-02-28 14:39
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("demo")
@ApiModel(value = "Demo", description = "测试")
@AllArgsConstructor
public class Demo extends SuperEntity<Long> {

    @NotBlank(message = "键不能为空")
    @TableField(value = "`key`")
    private String key;

    @NotBlank(message = "值不能为空")
    @TableField(value = "`value`")
    private String value;

}
