package cn.yiidii.pigeon.demo.handler;

import cn.yiidii.pigeon.common.strategy.annotation.HandlerType;
import cn.yiidii.pigeon.common.strategy.handler.AbstractHandler;
import org.springframework.stereotype.Component;

/**
 * @author YiiDii Wang
 * @create 2021-06-06 13:30
 */
@Component("B01Handler")
@HandlerType(bizCode = "B01", beanName = "B01Handler")
public class B01Handler implements AbstractHandler {

    @Override
    public Object handle(Object o) {
        return null;
    }
}
