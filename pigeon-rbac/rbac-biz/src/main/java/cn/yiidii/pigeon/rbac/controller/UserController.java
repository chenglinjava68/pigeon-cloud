package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import cn.yiidii.pigeon.rbac.service.IUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final DozerUtils dozerUtils;

    @PostMapping
    @ApiOperation(value = "创建用户")
    public R<UserVO> create(@Validated @RequestBody UserForm userForm) {
        Assert.isTrue(StringUtils.equals(userForm.getPassword(), userForm.getConfirmPassword()), "两次输入密码不一致");
        User user = userService.create(userForm);
        return R.ok(dozerUtils.map(user, UserVO.class));
    }

    @GetMapping
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "获取当前用户信息", notes = "需要登陆，且需要[user]权限")
    public R<UserDTO> info() {
        Object principal = SecurityUtils.getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getUserDTOByUsername(principal.toString());
        return R.ok(userDTO);
    }

    @GetMapping("/router")
    @ApiOperation(value = "用户路由菜单")
    public R<List<VueRouter>> router() {
        return R.ok(userService.getRouter(SecurityUtils.getUser().getId()));
    }

    @GetMapping("/list")
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "用户列表", notes = "需要登陆，且需要[user]权限")
    public R<IPage<UserVO>> list(BaseSearchParam searchParam) {
        return R.ok(userService.list(searchParam));
    }

}
