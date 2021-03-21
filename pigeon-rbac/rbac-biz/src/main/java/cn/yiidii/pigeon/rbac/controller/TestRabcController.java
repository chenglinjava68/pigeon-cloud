package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * test
 *
 * @author: YiiDii Wang
 * @create: 2021-02-11 16:05
 */
@RestController
@RequestMapping("test")
@Slf4j
@Api(tags = "测试")
@RequiredArgsConstructor
public class TestRabcController {

    private final IUserService userService;

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

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "getUserInfo")
    public R<User> getUserInfo(@RequestParam Long id) {
        return R.ok(userService.getBaseMapper().selectById(id));
    }

    @PostMapping("/createUser")
    @ApiOperation(value = "create")
    public R<User> create(@RequestBody UserForm userForm) {
        System.out.println(JSONObject.toJSON(userForm));
        return R.ok(userService.create(userForm));
    }

}
