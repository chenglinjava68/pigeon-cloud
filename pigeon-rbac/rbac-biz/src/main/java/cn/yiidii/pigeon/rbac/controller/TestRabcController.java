package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 *
 * @author: YiiDii Wang
 * @create: 2021-02-11 16:05
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestRabcController {

    @GetMapping("hello")
    public R<String> hello() {
        return R.ok(null, "hello rbac");
    }

    @GetMapping("biz")
    public R<String> biz(@RequestParam Integer code) {
        if (code == 0) {
            throw new BizException("biz exception");
        }
        return R.ok(null, "biz result");
    }

}
