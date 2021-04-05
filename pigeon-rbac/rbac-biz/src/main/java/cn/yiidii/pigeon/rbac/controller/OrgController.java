package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.rbac.api.entity.Org;
import cn.yiidii.pigeon.rbac.api.vo.OrgVO;
import cn.yiidii.pigeon.rbac.service.IOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final DozerUtils dozerUtils;

    @GetMapping("tree")
    @ApiOperation(value = "组织树")
    public R orgTree() {
        List<Org> list = orgService.lambdaQuery().list();
        return R.ok(TreeUtil.buildTree(dozerUtils.mapList(list, OrgVO.class)));
    }

}
