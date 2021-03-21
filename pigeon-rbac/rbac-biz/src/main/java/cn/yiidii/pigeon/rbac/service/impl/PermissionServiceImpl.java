package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.Permission;
import cn.yiidii.pigeon.rbac.mapper.PermissionMapper;
import cn.yiidii.pigeon.rbac.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 资源服务实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:01
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
