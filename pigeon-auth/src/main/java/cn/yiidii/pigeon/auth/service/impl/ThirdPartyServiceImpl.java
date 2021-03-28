package cn.yiidii.pigeon.auth.service.impl;

import cn.yiidii.pigeon.auth.service.IThirdPartyService;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.enumeration.Sex;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
@Service
@RequiredArgsConstructor
public class ThirdPartyServiceImpl implements IThirdPartyService {

    private final UserFeign userFeign;
    private final TokenEndpoint tokenEndpoint;

    @SneakyThrows
    @Override
    public void handle(AuthUser authUser) {
        String username = authUser.getUsername();
        UserDTO userDTOExist = userFeign.getUserDTOByUsername(username).getData();
        if (Objects.isNull(userDTOExist)) {
            // 用户不存在，新增
            userDTOExist = transAuthUserToUserDTO(authUser);
            R<UserDTO> resp = userFeign.create(userDTOExist);
            if (resp.getCode() != 0) {
                throw new BizException(resp.getMsg());
            }
        }
    }

    @Override
    public UserDTO save(AuthUser authUser) {
        UserDTO userDTO = transAuthUserToUserDTO(authUser);
        R<UserDTO> resp = userFeign.create(userDTO);
        if (resp.getCode() != 0) {
            throw new BizException(resp.getMsg());
        }
        return resp.getData();
    }

    private UserDTO transAuthUserToUserDTO(AuthUser authUser) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(authUser.getUsername());
        userDTO.setPassword(authUser.getUsername());
        userDTO.setSex(Sex.N);
        userDTO.setEmail(authUser.getEmail());
        userDTO.setAvatar(authUser.getAvatar());
        return userDTO;
    }
}
