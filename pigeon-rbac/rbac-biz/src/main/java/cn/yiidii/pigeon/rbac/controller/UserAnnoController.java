package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-15 17:40
 */
@RestController
@RequestMapping("/user-anno")
@RequiredArgsConstructor
@Slf4j
public class UserAnnoController {
    private final IUserService userService;
    private final DozerUtils dozerUtils;

    @GetMapping("/info/{username}")
    @ApiIgnore
    private R<UserDTO> userInfo(@PathVariable @NotBlank(message = "用户名不能为空") String username) {
        UserDTO userDTO = userService.getUserDTOByUsername(username);
        return R.ok(userDTO);
    }

    @GetMapping("/getUserDTOByPlatform")
    @ApiIgnore
    private R<UserDTO> getUserDTOByPlatform(@RequestParam @NotBlank(message = "平台名称不能为空") String platformName, @RequestParam @NotBlank(message = "uuid不能为空") String uuid) {
        return R.ok(userService.getUserDTOByPlatform(platformName, uuid));
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建用户")
    public R<cn.yiidii.pigeon.rbac.api.vo.UserVO> create(@Validated @RequestBody UserForm userForm) {
        Assert.isTrue(StringUtils.equals(userForm.getPassword(), userForm.getConfirmPassword()), "两次输入密码不一致");
        User user = userService.create(userForm);
        return R.ok(dozerUtils.map(user, cn.yiidii.pigeon.rbac.api.vo.UserVO.class));
    }
}
