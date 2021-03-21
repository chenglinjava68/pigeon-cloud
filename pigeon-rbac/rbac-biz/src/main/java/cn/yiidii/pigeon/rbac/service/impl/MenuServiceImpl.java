package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.rbac.api.entity.Menu;
import cn.yiidii.pigeon.rbac.mapper.MenuMapper;
import cn.yiidii.pigeon.rbac.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
