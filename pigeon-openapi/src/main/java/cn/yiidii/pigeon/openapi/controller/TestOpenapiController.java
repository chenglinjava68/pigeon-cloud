package cn.yiidii.pigeon.openapi.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.SpringContextHolder;
import cn.yiidii.pigeon.openapi.constant.TelecomEnum;
import cn.yiidii.pigeon.openapi.model.form.TelecomLoginForm;
import cn.yiidii.pigeon.openapi.service.ITelecomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-09 09:46
 */
@RestController
@Api(tags = "运营商工具")
@RequiredArgsConstructor
@Slf4j
public class TestOpenapiController {

    @GetMapping("/test/hello")
    @ApiOperation(value = "hello")
    public R hello() {
        return R.ok("hello openapi");
    }

}
