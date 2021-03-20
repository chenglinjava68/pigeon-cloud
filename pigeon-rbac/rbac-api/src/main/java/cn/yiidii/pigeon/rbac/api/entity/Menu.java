package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

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
@TableName("menu")
@ApiModel(value = "Menu", description = "菜单")
@AllArgsConstructor
public class Menu extends TreeEntity<Menu, Long> {

    @ApiModelProperty(value = "资源code")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "是否缓存该页面: 1:是  0:不是")
    private String keepAlive;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "是否隐藏")
    private String hidden;

    @ApiModelProperty(value = "是否外链")
    private String target;

}
