package cn.yiidii.pigeon.rbac.api.feign.factory;

import cn.yiidii.pigeon.rbac.api.feign.SysLogFeign;
import cn.yiidii.pigeon.rbac.api.feign.fallback.SysLogFeignFallBack;
import feign.hystrix.FallbackFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-12 23:40
 */
@Component
@RequiredArgsConstructor
public class SysLogFeignFallbackFactory implements FallbackFactory<SysLogFeign> {

    @Override
    public SysLogFeign create(Throwable throwable) {
        SysLogFeignFallBack fallBack = new SysLogFeignFallBack();
        fallBack.setCause(throwable);
        return fallBack;
    }

}
