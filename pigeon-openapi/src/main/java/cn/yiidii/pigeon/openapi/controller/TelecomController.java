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

import javax.validation.Valid;

/**
 * 运营商接口
 *
 * @author: YiiDii Wang
 * @create: 2021-03-07 16:52
 */
@RestController
@RequestMapping("telecom")
@Api(tags = "运营商工具")
@RequiredArgsConstructor
@Slf4j
public class TelecomController {

    @GetMapping("/captcha")
    @ApiOperation(value = "获取图形验证码")
    public R captcha(@RequestBody @Validated TelecomLoginForm telecomLoginForm) {
        ITelecomService telecomService = SpringContextHolder.getBean(TelecomEnum.valueOf(telecomLoginForm.getType()).getProcessor(), ITelecomService.class);
        return R.ok(telecomService.getCaptcha(), null);
    }

    @PostMapping("/sendRandomNum")
    @ApiOperation(value = "发手机验证码")
    public R sendRandomNum(@RequestBody @Validated TelecomLoginForm telecomLoginForm) {
        ITelecomService telecomService = SpringContextHolder.getBean(TelecomEnum.valueOf(telecomLoginForm.getType()).getProcessor(), ITelecomService.class);
        return R.ok(null, telecomService.sendRandomNum(telecomLoginForm));
    }

    @PostMapping("/randomLogin")
    @ApiOperation(value = "验证码登陆")
    public R randomLogin(@RequestBody @Validated TelecomLoginForm telecomLoginForm) {
        ITelecomService telecomService = SpringContextHolder.getBean(TelecomEnum.valueOf(telecomLoginForm.getType()).getProcessor(), ITelecomService.class);
        return telecomService.randomLogin(telecomLoginForm);
    }


}
