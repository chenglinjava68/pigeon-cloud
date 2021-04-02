package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.Org;
import cn.yiidii.pigeon.rbac.mapper.OrgMapper;
import cn.yiidii.pigeon.rbac.service.IOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 组织服务实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 22:01
 */
@Service
@RequiredArgsConstructor
public class OrgServiceImpl extends ServiceImpl<OrgMapper, Org> implements IOrgService {

}
