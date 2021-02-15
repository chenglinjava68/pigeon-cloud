package cn.yiidii.pigeon.rbac.api.feign;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.constant.ServiceNameConstant;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.factory.UserFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * rbac-用户远程调用
 *
 * @author: YiiDii Wang
 * @create: 2021-01-16 01:02
 */
@FeignClient(contextId = "UserFeignService",
        value = ServiceNameConstant.SERVICE_RBAC,
        fallbackFactory = UserFeignFallbackFactory.class)
public interface UserFeign {

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名
     * @return
     */
    @GetMapping("/user-anno/info/{username}")
    R<UserDTO> getUserDTOByUsername(@PathVariable("username") String username);

}
