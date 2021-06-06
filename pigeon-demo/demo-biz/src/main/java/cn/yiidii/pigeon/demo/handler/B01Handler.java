package cn.yiidii.pigeon.demo.handler;

import cn.yiidii.pigeon.common.strategy.annotation.HandlerType;
import cn.yiidii.pigeon.common.strategy.handler.AbstractHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author YiiDii Wang
 * @create 2021-06-06 13:30
 */
@Component("B01Handler")
@HandlerType(bizCode = "A01", beanName = "B01Handler")
public class B01Handler implements AbstractHandler {

    @Override
    public Object handle(Object o) {
        return null;
    }
}
