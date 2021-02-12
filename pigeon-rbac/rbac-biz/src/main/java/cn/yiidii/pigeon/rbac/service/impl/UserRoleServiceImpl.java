package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.UserRole;
import cn.yiidii.pigeon.rbac.service.IUserRoleService;
import cn.yiidii.pigeon.rbac.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户角色业务实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:57
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
}
