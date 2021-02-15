package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.service.IUserService;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 用户
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 15:30
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户接口")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;

    @GetMapping("/info/{username}")
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String", defaultValue = "")
    private R<UserDTO> userInfo(@PathVariable @NotBlank(message = "用户名不能为空") String username) {
        UserDTO userDTO = userService.getUserDTOByUsername(username);
        return R.ok(userDTO);
    }

}
