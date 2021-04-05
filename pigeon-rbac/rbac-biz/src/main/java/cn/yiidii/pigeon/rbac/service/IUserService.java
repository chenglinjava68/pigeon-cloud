package cn.yiidii.pigeon.rbac.service;


import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.form.param.UserSearchParam;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import cn.yiidii.pigeon.rbac.api.vo.VueRouter;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户业务接口
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:57
 */
public interface IUserService extends IService<User> {

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据手机号获取用户
     *
     * @param mobile 手机号
     * @return
     */
    User getUserByMobile(String mobile);

    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    UserDTO getUserDTOByUsername(String username);

    /**
     * 根据平台和uuid获取用户
     * @param platformName
     * @param uuid
     * @return
     */
    UserDTO getUserDTOByPlatform(String platformName, String uuid);

    /**
     * 创建用户
     *
     * @param userDTO
     * @return
     */
    User create(UserDTO userDTO);

    /**
     * 创建用户
     *
     * @param userForm
     * @return
     */
    User create(UserForm userForm);

    /**
     * 更新用户
     *
     * @param userForm
     * @return
     */
    User update(UserForm userForm);

    /**
     * 删除用户
     *
     * @param uidList    用户ID
     * @return
     */
    void deleteUser(List<Long> uidList);

    /**
     * 用户列表
     *
     * @param searchParam
     * @return
     */
    IPage<UserVO> list(UserSearchParam searchParam);

    /**
     * 获取用户路由
     *
     * @param uid
     * @return
     */
    List<VueRouter> getRouter(Long uid);

}
