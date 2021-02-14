package cn.yiidii.pigeon.demo.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.redis.RedisOps;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:35
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestDemoController {

    private final UserFeign userFeign;

    private final RedisOps redisOps;

    @GetMapping("/test/hello")
    public R<String> hello() {
        return R.ok(null, "hello demo");
    }

    @GetMapping("/test/redis")
    public R<String> redis(@RequestParam String key, @RequestParam String value) {
        redisOps.set(key, value);
        Map<String, String> map = new HashMap<>();
        map.put(key, value);
        redisOps.putHashValues("hash", map);
        return R.ok(null, JSONObject.toJSONString(map));
    }

    /**
     * 需要登陆，但不需要权限
     *
     * @return
     */
    @GetMapping("aaa")
    public R<UserDTO> aaa() {
        return R.ok(null, "aaa");
    }

    /**
     * 需要登陆，但不需要权限
     *
     * @return
     */
    @GetMapping("bbb")
    @PreAuthorize("hasAuthority('bbb')")
    public R<UserDTO> bbb() {
        return R.ok(null, "bbb");
    }

    /**
     * 需要登陆，且需要user权限
     *
     * @param username
     * @return
     */
    @GetMapping("user/{username}")
    @PreAuthorize("hasAuthority('user')")
    public R<UserDTO> user(@PathVariable String username) {
        R<UserDTO> userDTOResp = userFeign.getUserDTOByUsername(username);
        log.info("test user: " + JSONObject.toJSON(userDTOResp));
        return userDTOResp;
    }


}
