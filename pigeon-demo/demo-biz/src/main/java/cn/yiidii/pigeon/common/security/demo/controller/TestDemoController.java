package cn.yiidii.pigeon.common.security.demo.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:35
 */
@RestController
@RequestMapping("test")
@Slf4j
@RequiredArgsConstructor
public class TestDemoController {

    private final UserFeign userFeign;

    @GetMapping("hello")
    public R<String> hello() {
        return R.ok(null, "hello demo");
    }

    @GetMapping("biz")
    public R<String> biz(@RequestParam Integer code) {
        if (code == 0) {
            throw new BizException("biz exception");
        }
        return R.ok(null, "biz result");
    }

    @GetMapping("user/{username}")
    public R<UserDTO> user(@PathVariable String username) {
        R<UserDTO> userDTOResp = userFeign.getUserDTOByUsername(username);
        log.info("test user: " + JSONObject.toJSON(userDTOResp));
        return userDTOResp;
    }


}
