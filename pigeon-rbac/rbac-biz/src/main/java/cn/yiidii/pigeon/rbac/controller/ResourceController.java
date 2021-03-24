package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.rbac.api.entity.Menu;
import cn.yiidii.pigeon.rbac.api.entity.Permission;
import cn.yiidii.pigeon.rbac.service.IMenuService;
import cn.yiidii.pigeon.rbac.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资源
 *
 * @author: YiiDii Wang
 * @create: 2021-03-06 20:20
 */
@RestController
@RequestMapping("resource")
@Api(tags = "资源")
@RequiredArgsConstructor
public class ResourceController {

    private final IMenuService menuService;
    private final IPermissionService permissionService;
    private final DozerUtils dozerUtils;

    @GetMapping("menuTree")
    @ApiOperation(value = "所有菜单树")
    public R menuTree() {
        List<Menu> allMenu = menuService.lambdaQuery().list();
        allMenu.sort(Comparator.comparing(TreeEntity::getSort));
        return R.ok(TreeUtil.buildTree(allMenu));
    }

    @GetMapping("menuPermsMap")
    @ApiOperation(value = "菜单-权限映射")
    public R menuPermsMap() {
        List<Permission> allPermission = permissionService.lambdaQuery().list();
        Map<Long, List<Permission>> permsMap = allPermission.stream().collect(Collectors.groupingBy(Permission::getMenuId));
        return R.ok(permsMap);
    }

}
