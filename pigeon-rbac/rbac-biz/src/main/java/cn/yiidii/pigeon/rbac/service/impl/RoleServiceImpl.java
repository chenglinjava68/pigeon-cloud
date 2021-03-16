package cn.yiidii.pigeon.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.common.core.util.TreeUtil;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.dto.RoleDTO;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import cn.yiidii.pigeon.rbac.api.entity.Role;
import cn.yiidii.pigeon.rbac.api.entity.UserRole;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import cn.yiidii.pigeon.rbac.mapper.RoleMapper;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import cn.yiidii.pigeon.rbac.service.IRoleService;
import cn.yiidii.pigeon.rbac.service.IUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色业务实现
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:48
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final IUserRoleService userRoleService;
    private final IResourceService resourceService;
    private final DozerUtils dozerUtils;

    @Override
    public List<Role> getRoleListByUid(Long uid) {
        Set<Integer> roleIdList = userRoleService.lambdaQuery().eq(UserRole::getUserId, uid).list().stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        return this.lambdaQuery().in(Role::getId, roleIdList).list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(RoleDTO roleDTO) {
        Role roleExist = this.lambdaQuery().eq(Role::getName, roleDTO.getName()).one();
        if (Objects.nonNull(roleExist)) {
            throw new BizException(StrUtil.format("角色已[{}]存在", roleDTO.getName()));
        }
        Role role = new Role();
        dozerUtils.map(roleDTO, role);
        Long currUid = SecurityUtils.getUser().getId();
        role.setCreateTime(LocalDateTime.now());
        role.setCreatedBy(currUid);
        role.setUpdateTime(LocalDateTime.now());
        role.setUpdatedBy(currUid);
        return this.getBaseMapper().insert(role);
    }

    @Override
    public IPage<Role> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), Role::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, Role::getName, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, Role::getId, searchParam.getKeyword());

        // 根据排序字段进行排序
        if (StringUtils.isNotBlank(searchParam.getOrderBy())) {
        }

        // 分页查询
        Page<Role> page = new Page<>(searchParam.getCurrent(), searchParam.getSize());
        Page<Role> rolePage = this.baseMapper.selectPage(page, queryWrapper);
        return rolePage;
    }

    @Override
    public List<VueRouter> getRouter() {
        Set<Long> ridSet = this.getRoleListByUid(SecurityUtils.getUser().getId()).stream().map(Role::getId).collect(Collectors.toSet());
        Set<Resource> roleResourceList = resourceService.getResourceByRids(ridSet);
        List<VueRouter> treeList = dozerUtils.mapList(roleResourceList, VueRouter.class);
        return TreeUtil.buildTree(treeList);
    }
}
