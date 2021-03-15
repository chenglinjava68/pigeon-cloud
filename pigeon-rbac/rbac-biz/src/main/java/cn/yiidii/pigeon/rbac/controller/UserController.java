package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity.Add;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public R<UserDTO> userInfo(@PathVariable @NotBlank(message = "用户名不能为空") String username) {
        UserDTO userDTO = userService.getUserDTOByUsername(username);
        return R.ok(userDTO);
    }

    @PostMapping("/create")
    @ApiOperation(value = "创建用户")
    public R<UserDTO> create(@Validated(value = {Add.class}) @RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        BeanUtils.copyProperties(user, userDTO);
        return R.ok(userDTO);
    }

    @GetMapping("/info")
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "获取当前用户信息", notes = "需要登陆，且需要[user]权限")
    public R<UserDTO> info() {
        Object principal = SecurityUtils.getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getUserDTOByUsername(principal.toString());
        return R.ok(userDTO);
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "用户列表", notes = "需要登陆，且需要[user]权限")
    public R<IPage<UserVO>> list(BaseSearchParam searchParam) {
        return R.ok(userService.list(searchParam));
    }

}
