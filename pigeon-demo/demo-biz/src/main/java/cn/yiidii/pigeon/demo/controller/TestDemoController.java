package cn.yiidii.pigeon.demo.controller;

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
@Slf4j
@RequiredArgsConstructor
public class TestDemoController {

    private final UserFeign userFeign;

    @GetMapping("/test/hello")
    public R<String> hello() {
        return R.ok(null, "hello demo");
    }

    @GetMapping("/test/biz")
    public R<String> biz(@RequestParam Integer code) {
        if (code == 0) {
            throw new BizException("biz exception");
        }
        return R.ok(null, "biz result");
    }

    @GetMapping("aaa")
    public R<UserDTO> aaa() {
        return R.ok(null, "aaa");
    }

    @GetMapping("user/{username}")
    public R<UserDTO> user(@PathVariable String username) {
        R<UserDTO> userDTOResp = userFeign.getUserDTOByUsername(username);
        log.info("test user: " + JSONObject.toJSON(userDTOResp));
        return userDTOResp;
    }


}
