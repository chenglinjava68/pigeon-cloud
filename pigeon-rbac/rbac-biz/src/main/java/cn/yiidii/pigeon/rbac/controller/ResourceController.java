package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    private final IResourceService resourceService;
    private final DozerUtils dozerUtils;

    @GetMapping
    @ApiOperation(value = "获取当前用户资源")
    public R<Collection<VueRouter>> getResourceByUid(){
        Set<Resource> resourceSet = resourceService.getResourceByUid(SecurityUtils.getUser().getId());
        List<VueRouter> treeList = dozerUtils.mapList(resourceSet, VueRouter.class);
        return R.ok(TreeUtil.buildTree(treeList));
    }

    @GetMapping
    @RequestMapping("tree")
    @ApiOperation(value = "所有菜单树")
    public R allTree(){
        List<Resource> allResource = resourceService.lambdaQuery().list();
        return R.ok(TreeUtil.buildTree(allResource));
    }

}
