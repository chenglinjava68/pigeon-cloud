package cn.yiidii.pigeon.rbac.api.feign;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.constant.ServiceNameConstant;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.factory.UserFeignFallbackFactory;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 通过平台获取用户
     *
     * @param platformName      用户名
     * @param uuid              uuid
     * @return
     */
    @GetMapping("/user-anno/getUserDTOByPlatform")
    R<UserDTO> getUserDTOByPlatform(@RequestParam("platformName") String platformName, @RequestParam("uuid") String uuid);

    /**
     * 创建用户
     * @param userForm
     * @return
     */
    @PostMapping("/user-anno/create")
    R<UserVO> create(@RequestBody UserForm userForm);

}
