package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity.Add;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity.Update;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.rbac.api.entity.Menu;
import cn.yiidii.pigeon.rbac.api.entity.Org;
import cn.yiidii.pigeon.rbac.api.entity.Permission;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.form.RoleForm;
import cn.yiidii.pigeon.rbac.api.form.RoleResourceForm;
import cn.yiidii.pigeon.rbac.api.form.RoleUserForm;
import cn.yiidii.pigeon.rbac.service.IOrgService;
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
 * 组织
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 15:30
 */
@RestController
@RequestMapping("org")
@Api(tags = "组织")
@RequiredArgsConstructor
@Slf4j
public class OrgController {

    private final IOrgService orgService;

    @GetMapping("tree")
    @ApiOperation(value = "组织树")
    public R orgTree() {
        List<Org> list = orgService.lambdaQuery().list();
        return R.ok(TreeUtil.buildTree(list));
    }

}
