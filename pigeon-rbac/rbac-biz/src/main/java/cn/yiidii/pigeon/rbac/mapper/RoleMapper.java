package cn.yiidii.pigeon.rbac.mapper;

import cn.yiidii.pigeon.rbac.api.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色mapper
 *
 * @author: YiiDii Wang
 * @create: 2021-01-13 21:49
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
