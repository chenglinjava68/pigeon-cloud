package cn.yiidii.pigeon.rbac.api.dto;

import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:37
 */
@Data
@ApiModel("资源DTO")
public class ResourceDTO implements Serializable {

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

    @ApiModelProperty(value = "状态")
    private Status status;
}
