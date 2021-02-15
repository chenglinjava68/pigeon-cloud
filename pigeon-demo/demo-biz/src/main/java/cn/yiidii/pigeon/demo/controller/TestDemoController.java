package cn.yiidii.pigeon.demo.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.redis.RedisOps;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
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
@Api(tags = "Demo测试接口")
public class TestDemoController {

    private final UserFeign userFeign;

    private final RedisOps redisOps;

    private final Environment env;

    @GetMapping("/test/hello")
    @ApiOperation(value = "测试hello接口")
    public R<String> hello() {
        return R.ok(null, "hello demo");
    }

    @GetMapping("/test/redis")
    @ApiOperation(value = "测试redis接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "缓存键", required = true, paramType = "query", dataType = "String", defaultValue = ""),
            @ApiImplicitParam(name = "value", value = "缓存值", required = true, paramType = "query", dataType = "String", defaultValue = "")
    })
    public R<String> redis(@RequestParam String key, @RequestParam String value) {
        redisOps.set(key, value);
        Map<String, String> map = new HashMap<>(16);
        map.put(key, value);
        redisOps.putHashValues("hash", map);
        return R.ok(null, JSONObject.toJSONString(map));
    }

    @GetMapping("/test/env")
    @ApiOperation(value = "获取环境变量")
    @ApiImplicitParam(name = "name", value = "变量名称", required = true, paramType = "query", dataType = "String", defaultValue = "")
    public R<String> env(@RequestParam String name) {
        return R.ok(null, env.getProperty(name));
    }

    /**
     * 需要登陆，但不需要权限
     *
     * @return
     */
    @GetMapping("aaa")
    @ApiOperation(value = "aaa接口", notes = "需要登陆，但不需要权限")
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
    @ApiOperation(value = "bbb接口", notes = "需要登陆，且需要[bbb]权限")
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
    @ApiOperation(value = "user接口", notes = "需要登陆，且需要[user]权限")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String", defaultValue = "")
    public R<UserDTO> user(@PathVariable String username) {
        R<UserDTO> userDTOResp = userFeign.getUserDTOByUsername(username);
        log.info("test user: " + JSONObject.toJSON(userDTOResp));
        return userDTOResp;
    }


}
