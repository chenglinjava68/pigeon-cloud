package cn.yiidii.pigeon.rbac.mapper;

import cn.yiidii.pigeon.rbac.api.bo.UserBO;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户mapper
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:55
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户ID获取用户权限
     *
     * @param userId    用户id
     * @return
     */
    List<String> getUserPermissionByUid(@Param("userId") Long userId);

    /**
     * 根据用户名获取UserBO
     * @param username
     * @return
     */
    UserBO getUserBOByUsername(String username);
}
