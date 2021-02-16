package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.rbac.api.entity.User;
import cn.yiidii.pigeon.rbac.service.IUserService;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 用户接口实现类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:58
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public UserDTO getUserDTOByUsername(String username) {
        return userMapper.getUserDTOByUsername(username);
    }

    @Override
    public User create(UserDTO userDTO) {
        String username = userDTO.getUsername();
        User userExist = this.getUserByUsername(username);
        if (Objects.nonNull(userExist)) {
            throw new BizException("用户已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setSalt("");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        this.save(user);
        return this.getUserByUsername(username);
    }
}
