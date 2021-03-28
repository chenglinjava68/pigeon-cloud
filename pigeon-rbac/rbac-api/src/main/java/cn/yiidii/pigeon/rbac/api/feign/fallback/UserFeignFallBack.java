package cn.yiidii.pigeon.rbac.api.feign.fallback;

import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
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
        return R.failed(StrUtil.format("查询用户[{}]信息失败, 原因: {}", username, cause.getMessage()));
    }

    /**
     * 通过平台获取用户
     *
     * @param platformName 用户名
     * @param uuid         uuid
     * @return
     */
    @Override
    public R<UserDTO> getUserDTOByPlatform(String platformName, String uuid) {
        return R.failed(StrUtil.format("获取用户失败[{}_{}], 原因：{}", platformName, uuid, cause.getMessage()));
    }

    @Override
    public R<cn.yiidii.pigeon.rbac.api.vo.UserVO> create(UserForm userForm) {
        return R.failed(StrUtil.format("添加用户失败[{}], 原因：{}", userForm.getUsername(), cause.getMessage()));
    }
}
