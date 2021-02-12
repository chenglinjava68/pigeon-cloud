package cn.yiidii.pigeon.rbac.api.feign.fallback;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 23:38
 */
@Component
@Slf4j
public class UserFeignFallBack implements UserFeign {

    @Setter
    private Throwable cause;

    @Override
    public R<UserDTO> getUserDTOByUsername(String username) {
        log.error("feign 查询用户信息失败:{},{}", username, cause);
        return R.failed(StrUtil.format("查询用户[{}]信息失败, 原因: {}", username,  cause.getMessage()));
    }
}
