package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.mapper.ResourceMapper;
import cn.yiidii.pigeon.rbac.service.IResourceService;
import cn.yiidii.pigeon.rbac.api.entity.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 资源服务实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:01
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
}
