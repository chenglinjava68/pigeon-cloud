package cn.yiidii.pigeon.rbac.api.feign;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.constant.ServiceNameConstant;
import cn.yiidii.pigeon.rbac.api.feign.factory.SysLogFeignFallbackFactory;
import cn.yiidii.pigeon.rbac.api.form.OptLogForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统日志feign
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:46
 */
@FeignClient(contextId = "SysLogService",
        value = ServiceNameConstant.SERVICE_RBAC,
        fallbackFactory = SysLogFeignFallbackFactory.class)
public interface SysLogFeign {

    /**
     * 创建操作日志
     * @param optLogForm
     * @return
     */
    @PostMapping("sysLog/opt")
    R createOptLog(@RequestBody OptLogForm optLogForm);
}
