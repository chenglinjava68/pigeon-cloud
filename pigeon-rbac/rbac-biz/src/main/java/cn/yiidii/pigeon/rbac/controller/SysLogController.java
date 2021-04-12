package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.form.OptLogForm;
import cn.yiidii.pigeon.rbac.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志控制器
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:35
 */
@RestController
@RequestMapping("sysLog")
@Api(tags = "系统日志")
@RequiredArgsConstructor
@Slf4j
public class SysLogController {

    private final ISysLogService sysLogService;

    @PostMapping("/opt")
    @ApiOperation(value = "创建操作日志")
    public R create(@RequestBody @Validated OptLogForm optLogForm){
        sysLogService.createOptLog(optLogForm);
        return R.ok("创建操作日志成功");
    }

}
