package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 资源
 *
 * @author: YiiDii Wang
 * @create: 2021-03-06 20:20
 */
@RestController("resource")
@Api(tags = "资源")
@RequiredArgsConstructor
public class ResourceController {

    private final IResourceService resourceService;

    @GetMapping
    @ApiOperation(value = "获取当前用户资源")
    public R<Set<Resource>> getResourceByUid(){
        Set<Resource> resourceSet = resourceService.getResourceByUid(SecurityUtils.getUser().getId());
        return R.ok(resourceSet);
    }

}
