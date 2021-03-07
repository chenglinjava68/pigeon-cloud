package cn.yiidii.pigeon.rbac.api.vo;

import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  vue前端路由
 *
 * @author YiiDii Wang
 * @date 2021/3/7 14:59:14
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VueRouter extends TreeEntity<VueRouter, Long> {

    private static final long serialVersionUID = -3327478146308500708L;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "元数据")
    private RouterMeta meta;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden = false;

    @ApiModelProperty(value = "总是显示")
    private Boolean alwaysShow = false;

    @Override
    @JsonIgnore
    public Long getId() {
        return this.id;
    }

    @Override
    @JsonIgnore
    public Long getParentId() {
        return this.parentId;
    }


    public Boolean getAlwaysShow() {
        return getChildren() != null && !getChildren().isEmpty();
    }

    public String getComponent() {
        if (getChildren() != null && !getChildren().isEmpty()) {
            return "Layout";
        }
        return this.component;
    }
}
