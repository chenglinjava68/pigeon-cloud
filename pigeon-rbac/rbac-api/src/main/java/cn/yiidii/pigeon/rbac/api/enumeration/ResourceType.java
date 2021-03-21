package cn.yiidii.pigeon.rbac.api.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 资源类型
 *
 * @author: YiiDii Wang
 * @create: 2021-03-21 15:18
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ResourceType", description = "资源类型枚举")
public enum ResourceType {

    /**
     * 菜单
     */
    MENU(10, "菜单"),
    /**
     * 权限
     */
    PERM(20, "权限"),
    ;

    @EnumValue
    private int code;
    @JsonValue
    private String desc;

}
