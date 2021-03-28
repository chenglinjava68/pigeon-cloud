package cn.yiidii.pigeon.rbac.api.enumeration;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

/**
 * 用户来源
 *
 * @author: YiiDii Wang
 * @create: 2021-03-28 16:43
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "UserSource", description = "用户来源枚举")
public enum UserSource {

    /**
     * 平台自有
     */
    PRIVATE(0, "平台自有"),
    /**
     * Github
     */
    GITHUB(1, "Github"),
    /**
     * Gitee
     */
    GITEE(2, "Gitee");

    @EnumValue
    private int code;
    private String desc;

    public static UserSource get(String val) {
        return match(val, null);
    }

    public static UserSource match(String val, UserSource def) {
        return Stream.of(values()).parallel().filter(item -> item.name().equalsIgnoreCase(val)).findAny().orElse(def);
    }

    public static void main(String[] args) {
        System.out.println(UserSource.get("aa"));
    }

}
