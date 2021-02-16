package cn.yiidii.pigeon.auth.service.impl;

import cn.yiidii.pigeon.auth.service.IThirdPartyService;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
@Service
@RequiredArgsConstructor
public class ThirdPartyServiceImpl implements IThirdPartyService {

    private final UserFeign userFeign;

    @Override
    public UserDTO save(AuthUser authUser) {
        UserDTO userDTO = transAuthUserToUserDTO(authUser);
        R<UserDTO> userDTOR = userFeign.create(userDTO);
        return userDTOR.getData();
    }

    private UserDTO transAuthUserToUserDTO(AuthUser authUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(authUser.getUsername());
        userDTO.setPassword(authUser.getUsername());
        userDTO.setSex(authUser.getGender().getCode());
        userDTO.setEmail(authUser.getEmail());
        userDTO.setAvatar(authUser.getAvatar());
        return userDTO;
    }
}
