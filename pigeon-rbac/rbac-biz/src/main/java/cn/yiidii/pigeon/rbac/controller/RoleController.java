package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity.*;
import cn.yiidii.pigeon.rbac.api.entity.Menu;
import cn.yiidii.pigeon.rbac.api.entity.Permission;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.form.RoleForm;
import cn.yiidii.pigeon.rbac.api.form.RoleResourceForm;
import cn.yiidii.pigeon.rbac.api.form.RoleUserForm;
import cn.yiidii.pigeon.rbac.api.form.param.RoleSearchParam;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 15:30
 */
@RestController
@RequestMapping("role")
@Api(tags = "角色")
@RequiredArgsConstructor
@Slf4j
public class RoleController {

    private final IRoleService roleService;

    @PostMapping
    @ApiOperation(value = "创建角色")
    @PreAuthorize("@pms.hasPermission('sys:role:add')")
    public R create(@Validated(value = {Add.class}) @RequestBody RoleForm roleForm) {
        int row = roleService.create(roleForm);
        return R.ok(null, row > 0 ? "创建角色成功" : "创建角色失败");
    }

    @PutMapping
    @ApiOperation(value = "更新角色")
    @PreAuthorize("@pms.hasPermission('sys:role:edit')")
    public R update(@Validated(value = {Update.class}) @RequestBody RoleForm roleForm) {
        boolean update = roleService.update(roleForm);
        return R.ok(null, update ? "更新角色成功" : "更新角色失败");
    }

    @PostMapping("/list")
    @ApiOperation(value = "角色列表")
    public R<IPage<Role>> list(@Validated @RequestBody RoleSearchParam searchParam) {
        return R.ok(roleService.list(searchParam));
    }

    @GetMapping("/user/{roleId}")
    @ApiOperation(value = "角色用户")
    public R<List<Long>> user(@PathVariable Long roleId) {
        return R.ok(roleService.getRoleUserIdList(roleId));
    }

    @GetMapping("/menu/{roleId}")
    @ApiOperation(value = "角色菜单")
    public R<List<Menu>> menu(@PathVariable Long roleId) {
        return R.ok(roleService.getRoleMenu(roleId));
    }

    @GetMapping("/perms/{roleId}")
    @ApiOperation(value = "角色权限")
    public R<List<Permission>> perms(@PathVariable Long roleId) {
        return R.ok(roleService.getRolePermission(roleId));
    }

    @PostMapping("/bindUser")
    @ApiOperation(value = "绑定用户")
    @PreAuthorize("@pms.hasPermission('sys:role:bindUser')")
    public R bindUser(@RequestBody RoleUserForm roleUserForm) {
        roleService.bindUser(roleUserForm);
        return R.ok(null, "绑定用户成功");
    }

    @PostMapping("/bindResource")
    @ApiOperation(value = "绑定菜单权限")
    @PreAuthorize("@pms.hasPermission('sys:role:bindResource')")
    public R bindResource(@RequestBody RoleResourceForm roleResourceForm) {
        roleService.bindResource(roleResourceForm);
        return R.ok(null, "绑定菜单权限成功");
    }

    @DeleteMapping("/delBatch")
    @ApiOperation(value = "删除角色")
    @PreAuthorize("@pms.hasPermission('sys:role:delete')")
    public R delRole(@RequestBody List<Long> roleIdList) {
        roleService.delRole(roleIdList);
        return R.ok(null, "删除角色成功");
    }

}
