package cn.yiidii.pigeon.rbac.api.feign.fallback;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.feign.SysLogFeign;
import cn.yiidii.pigeon.rbac.api.form.OptLogForm;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 系统日志feign熔断
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:48
 */
@Component
@Slf4j
public class SysLogFeignFallBack implements SysLogFeign {

    @PostConstruct
    public void init() {
        log.info("====== SysLogFeignFallBack init...");
    }


    @Setter
    private Throwable cause;

    @Override
    public R createOptLog(OptLogForm optLogForm) {
        log.error("创建操作日志失败: optLogForm{}; cause: {}", optLogForm, cause);
        return R.failed("创建操作日志失败");
    }

}
