package cn.yiidii.pigeon.auth.service.impl;

import cn.yiidii.pigeon.auth.exception.UnSupportedPlatformException;
import cn.yiidii.pigeon.auth.service.IThirdPartyService;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.constant.StringPool;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.common.core.util.DozerUtils;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.enumeration.Sex;
import cn.yiidii.pigeon.rbac.api.enumeration.UserSource;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-16 13:29
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ThirdPartyServiceImpl implements IThirdPartyService {

    private final UserFeign userFeign;
    private final DozerUtils dozerUtils;

    @SneakyThrows
    @Override
    public void handle(AuthUser authUser) {
        String source = authUser.getSource();
        UserSource userSource = UserSource.get(source);
        if (Objects.isNull(userSource)) {
            throw new UnSupportedPlatformException();
        }
        this.initUser(authUser);
    }

    public UserVO initUser(AuthUser authUser) {
        // 理论上 source + uuid 唯一定位一个用户
        String username = authUser.getUsername();
        String actualUsername = authUser.getSource().concat(StringPool.UNDERSCORE).concat(authUser.getUuid());
        UserDTO userDTOByPlatform = userFeign.getUserDTOByPlatform(authUser.getSource(), authUser.getUuid()).getData();

        if (Objects.nonNull(userDTOByPlatform)) {
            return dozerUtils.map(userDTOByPlatform, UserVO.class);
        }
        log.info("社交登陆({})新增用户:{}", authUser.getSource(), JSONObject.toJSON(authUser));
        // 不存在则新增用户
        String nickname = authUser.getNickname();
        UserForm userForm = UserForm.builder()
                .username(actualUsername)
                .password(username)
                .confirmPassword(username)
                .name(StringUtils.isNotBlank(nickname) ? nickname : username)
                .sex(Sex.N)
                .avatar(authUser.getAvatar())
                .desc("")
                .email(authUser.getEmail())
                .source(UserSource.get(authUser.getSource()))
                .uuid(authUser.getUuid())
                .build();
        R<UserVO> resp = userFeign.create(userForm);
        if (resp.getCode() != 0) {
            throw new BizException(resp.getMsg());
        }
        return resp.getData();
    }

}
