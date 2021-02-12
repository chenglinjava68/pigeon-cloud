package cn.yiidii.pigeon.auth.controller;

import cn.yiidii.pigeon.common.core.base.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-12 00:39
 */
@Slf4j
@RestController
@RequestMapping("/aaa")
public class TestController {

    @GetMapping("/hello")
    public R<String> hello() {
        return R.ok(null, "auth /aaa/hello");
    }

}
