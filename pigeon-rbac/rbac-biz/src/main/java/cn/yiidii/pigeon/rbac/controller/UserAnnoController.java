package cn.yiidii.pigeon.rbac.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.service.IUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-15 17:40
 */
@RestController
@RequestMapping("/user-anno")
@RequiredArgsConstructor
@Slf4j
public class UserAnnoController {
    private final IUserService userService;

    @GetMapping("/info/{username}")
    @ApiIgnore
    private R<UserDTO> userInfo(@PathVariable @NotBlank(message = "用户名不能为空") String username) {
        UserDTO userDTO = userService.getUserDTOByUsername(username);
        return R.ok(userDTO);
    }
}
