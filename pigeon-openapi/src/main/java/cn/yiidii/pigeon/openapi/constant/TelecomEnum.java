package cn.yiidii.pigeon.openapi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 运营商枚举
 *
 * @author: YiiDii Wang
 * @create: 2021-03-07 18:49
 */
@Getter
@AllArgsConstructor
public enum  TelecomEnum {
    XXX(1, "中国移动业务", "XxxProcessor"),
    YYY(2, "中国联通业务", "chinaUnicomService"),
    ZZZ(3, "中国电信业务", "ZzzProcessor");
    /**
     * 业务type
     */
    private Integer type;

    /**
     * 业务描述
     */
    private String name;

    /**
     * 对应处理器
     * {@link TelecomEnum}
     */
    private String processor;

    public static TelecomEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        for (TelecomEnum dataProcessorEnum : TelecomEnum.values()) {
            if (type.equals(dataProcessorEnum.getType())) {
                return dataProcessorEnum;
            }
        }
        return null;
    }
}
