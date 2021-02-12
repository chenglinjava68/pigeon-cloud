package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.mapper.OauthClientDetailsMapper;
import cn.yiidii.pigeon.rbac.api.entity.OauthClientDetails;
import cn.yiidii.pigeon.rbac.service.IOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  客户端详情 夜壶实现类
 *
 * @author YiiDii Wang
 * @date 2021/1/17 13:00:08
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {
}
