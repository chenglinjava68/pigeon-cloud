package cn.yiidii.pigeon.demo.handler.c;

import cn.yiidii.pigeon.common.strategy.annotation.HandlerType;
import org.springframework.stereotype.Component;

/**
 * C01
 *
 * @author YiiDii Wang
 * @create 2021-06-06 14:35
 */
@Component("C02Handler")
@HandlerType(bizCode = "C02", beanName = "C02Handler")
public class C02Handler implements SuperCHandler {

    @Override
    public void handle() {
        System.out.println("C02Handler handle...");
    }

}
