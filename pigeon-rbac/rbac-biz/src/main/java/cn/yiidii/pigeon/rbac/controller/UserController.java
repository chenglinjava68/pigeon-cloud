package cn.yiidii.pigeon.rbac.controller;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.log.annotation.Log;
import cn.yiidii.pigeon.rbac.api.dto.PermissionDTO;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.form.param.UserSearchParam;
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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 15:30
 */
@RestController
@RequestMapping("user")
@Api(tags = "用户")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;
    private final DozerUtils dozerUtils;

    @PostMapping
    @ApiOperation(value = "创建用户")
    @PreAuthorize("@pms.hasPermission('sys:user:add')")
    @Log(content = "'创建用户' + #userForm.username + '(' + #userForm.name + ')'", exception = "")
    public R<UserVO> create(@Validated @RequestBody UserForm userForm) {
        Assert.isTrue(StringUtils.equals(userForm.getPassword(), userForm.getConfirmPassword()), "两次输入密码不一致");
        User user = userService.create(userForm);
        return R.ok(dozerUtils.map(user, cn.yiidii.pigeon.rbac.api.vo.UserVO.class), StrUtil.format("创建用户{}成功", user.getName()));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "编辑用户")
    @PreAuthorize("@pms.hasPermission('sys:user:edit')")
    @Log(content = "'编辑用户:' + #userForm.username + '(' + #userForm.name + ')'", exception = "")
    public R<UserVO> updateUser(@Validated(value = SuperEntity.Update.class) @RequestBody UserForm userForm) {
        userService.update(userForm);
        return R.ok(null, "编辑成功");
    }

    @DeleteMapping("/delBatch")
    @ApiOperation(value = "删除用户")
    @PreAuthorize("@pms.hasPermission('sys:user:delete')")
    @Log(content = "'删除用户: ' + #uidList", exception = "")
    public R<UserVO> delete(@RequestBody List<Long> uidList) {
        userService.deleteUser(uidList);
        return R.ok(null, "删除用户成功");
    }

    @GetMapping
    @ApiOperation(value = "获取当前用户信息")
    @Log(content = "'获取当前用户信息'", exception = "")
    public R<UserVO> info() {
        Object principal = SecurityUtils.getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getUserDTOByUsername(principal.toString());
        List<PermissionDTO> permissionDTOList = userDTO.getPermissions();

        UserVO userVO = dozerUtils.map(userDTO, UserVO.class);
        if (!CollectionUtils.isEmpty(permissionDTOList)) {
            userVO.setPermissions(permissionDTOList.stream().map(PermissionDTO::getCode).collect(Collectors.toList()));
        }
        return R.ok(userVO);
    }

    @GetMapping("/router")
    @ApiOperation(value = "用户路由菜单")
    public R<List<VueRouter>> router() {
        return R.ok(userService.getRouter(SecurityUtils.getUser().getId()));
    }

    @PostMapping("/list")
    @ApiOperation(value = "用户列表", notes = "需要登陆，且需要[user]权限")
    public R<IPage<UserVO>> list(@RequestBody @Validated UserSearchParam searchParam) {
        return R.ok(userService.list(searchParam));
    }

}
