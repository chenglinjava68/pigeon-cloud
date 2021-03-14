package cn.yiidii.pigeon.rbac.service;


import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户业务接口
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:57
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户
     * @param username  用户名
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据用户名获取用户
     * @param username  用户名
     * @return
     */
    UserDTO getUserDTOByUsername(String username);

    /**
     * 创建用户
     * @param userDTO
     * @return
     */
    User create(UserDTO userDTO);

    /**
     * 用户列表
     * @param userDTO
     * @return
     */
    IPage<User> list(BaseSearchParam searchParam);

}
