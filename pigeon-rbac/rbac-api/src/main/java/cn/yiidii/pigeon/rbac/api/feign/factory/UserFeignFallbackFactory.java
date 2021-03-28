package cn.yiidii.pigeon.rbac.api.feign.factory;

import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import cn.yiidii.pigeon.rbac.api.feign.fallback.UserFeignFallBack;
import feign.hystrix.FallbackFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-12 23:40
 */
@Component
@RequiredArgsConstructor
public class UserFeignFallbackFactory implements FallbackFactory<UserFeign> {


    @Override
    public UserFeign create(Throwable throwable) {
        UserFeignFallBack userFeignFallBack = new UserFeignFallBack();
        userFeignFallBack.setCause(throwable);
        return userFeignFallBack;
    }

}
