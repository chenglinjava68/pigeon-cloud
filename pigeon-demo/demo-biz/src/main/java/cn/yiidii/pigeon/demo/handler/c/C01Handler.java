package cn.yiidii.pigeon.demo.handler.c;

import cn.yiidii.pigeon.common.strategy.annotation.HandlerType;
import org.springframework.stereotype.Component;

/**
 * C01
 *
 * @author YiiDii Wang
 * @create 2021-06-06 14:35
 */
@Component("C01Handler")
@HandlerType(bizCode = "C01", beanName = "C01Handler")
public class C01Handler implements SuperCHandler {

    @Override
    public void handle() {
        System.out.println("C01Handler handle...");
    }

}
