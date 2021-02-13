package cn.yiidii.pigeon.gateway.fallback;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.code.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 响应超时熔断处理器
 *
 * @author YiiDii Wang
 * @date 2021/1/20 11:36:08
 */
@RestController
@Slf4j
public class FallbackController {

    @RequestMapping("/fallback")
    public Mono<R> fallback() {
        log.info("fallback");
        return Mono.just(R.failed(ExceptionCode.SYSTEM_TIMEOUT));
    }
}
