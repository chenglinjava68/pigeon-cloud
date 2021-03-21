package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 权限
 *
 * @author: YiiDii Wang
 * @create: 2021-03-21 15:32
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("permission")
@ApiModel(value = "Permission", description = "权限")
@AllArgsConstructor
public class Permission extends SuperEntity<Long> {

    @ApiModelProperty(value = "权限编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "权限描述")
    @TableField(value = "`desc`")
    private String desc;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
