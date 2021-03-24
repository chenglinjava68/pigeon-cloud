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
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
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
    public OAuth2AccessToken handle(AuthUser authUser) {
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

        // 登陆（TODO 将来这里应该会做成免密的登陆，因为从第三方获取到用户信息之后，就视为登陆成功了）
        Principal principal = () -> username;
        Map<String, String> params = new HashMap<>(16);
        params.put("client_id", "system");
        params.put("client_secret", "system");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", userDTOExist.getPassword());
        OAuth2AccessToken accessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
        return accessToken;
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
