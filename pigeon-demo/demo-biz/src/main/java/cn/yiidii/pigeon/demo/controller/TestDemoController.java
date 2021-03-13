package cn.yiidii.pigeon.demo.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.redis.RedisOps;
import cn.yiidii.pigeon.common.core.util.HttpClientUtil;
import cn.yiidii.pigeon.common.core.util.dto.HttpClientResult;
import cn.yiidii.pigeon.common.mail.core.MailTemplate;
import cn.yiidii.pigeon.common.security.service.PigeonUser;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.demo.api.dto.EmailDTO;
import cn.yiidii.pigeon.demo.api.entity.Demo;
import cn.yiidii.pigeon.demo.mapper.DemoMapper;
import cn.yiidii.pigeon.demo.message.producer.IMailProducer;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import com.alibaba.fastjson.JSONObject;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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

    private final DemoMapper demoMapper;

    private final MailTemplate mailTemplate;

    private final IMailProducer mailProducer;

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

    @GetMapping("/test/url")
    @ApiOperation(value = "测试httpclient")
    @SneakyThrows
    public R<HttpClientResult> url() {
        HttpClientResult httpClientResult = HttpClientUtil.doGet("http://openapi.yiidii.cn:65531/xjj/cdk/info?cdkId=114130");
        return R.ok(httpClientResult, "");
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
    @PreAuthorize("@pms.hasPermission('bbb')")
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
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "user接口", notes = "需要登陆，且需要[user]权限")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "path", dataType = "String", defaultValue = "")
    public R<UserDTO> user(@PathVariable String username) {
        R<UserDTO> userDTOResp = userFeign.getUserDTOByUsername(username);
        log.info("test user: " + JSONObject.toJSON(userDTOResp));
        return userDTOResp;
    }

    @GetMapping("user/info")
    @PreAuthorize("@pms.hasPermission('user')")
    @ApiOperation(value = "获取当前用户信息", notes = "需要登陆，且需要[user]权限")
    public R<UserDTO> info() {
        Object principal = SecurityUtils.getAuthentication().getPrincipal();
        log.info("principal: " + JSONObject.toJSON(principal));
        PigeonUser user = SecurityUtils.getUser();
        log.info("user: " + JSONObject.toJSON(user));
        R<UserDTO> userDTOByUsername = userFeign.getUserDTOByUsername(principal.toString());
        log.info("userDTOByUsername: " + JSONObject.toJSON(userDTOByUsername));
        return userDTOByUsername;
    }

    @GetMapping("/test/createDemo0")
    @ApiOperation(value = "新增demo0", notes = "Transactional 正常插入")
    @Transactional
    public R createDemo0() {
        Demo demo = new Demo("name", "tom");
        demo.setCreateTime(LocalDateTime.now());
        demo.setCreatedBy(114130L);
        int insert = demoMapper.insert(demo);
        if (insert > 0) {
            log.info("demo数据插入成功");
        }
        return R.ok();
    }

    @GetMapping("/test/createDemo1")
    @ApiOperation(value = "新增demo1", notes = "Transactional 插入成功，但异常回滚")
    @Transactional
    public R createDemo1() {
        Demo demo = new Demo("name", "tom");
        demo.setCreateTime(LocalDateTime.now());
        demo.setCreatedBy(114130L);
        int insert = demoMapper.insert(demo);
        if (insert > 0) {
            log.info("demo数据插入成功");
        }
        int a = 1 / 0;
        return R.ok();
    }

    @GetMapping("/test/createDemo2")
    @ApiOperation(value = "新增demo2", notes = "GlobalTransactional 插入成功，但异常回滚")
    @GlobalTransactional
    public R createDemo2() {
        Demo demo = new Demo("name", "tom");
        demo.setCreateTime(LocalDateTime.now());
        demo.setCreatedBy(114130L);
        int insert = demoMapper.insert(demo);
        if (insert > 0) {
            log.info("demo数据插入成功");
        }
        int a = 1 / 0;
        return R.ok();
    }

    @SneakyThrows
    @GetMapping("/test/seata")
    @ApiOperation(value = "测试seata", notes = "先本地插入数据，再调用rbac服务插入数据")
    @GlobalTransactional(rollbackFor = Exception.class)
    public R seata() {

        log.info("当前 XID: {}", RootContext.getXID());

        // 插入demo数据是会成功的
        Demo demo = new Demo("name", "tom");
        demo.setCreateTime(LocalDateTime.now());
        demo.setCreatedBy(114130L);
        int insert = demoMapper.insert(demo);
        if (insert > 0) {
            log.info("demo数据插入成功");
        }

        // wangyidi用户已存在，会抛出异常
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(String.valueOf(new Random().nextInt()));
        userDTO.setPassword("11");
        R<UserDTO> userDTOR = userFeign.create(userDTO);
        log.info("userDTOR: {}", JSONObject.toJSON(userDTOR));
        if (userDTOR.getCode() != 0) {
            /*
             在controller层做统一的全局异常处理，封装提示信息响应到前端，通过@ControllerAdvice注解实现，可以区分同步的异常进行拦截从而产生不同的响应。
             有一点需要注意，当全局事物A调用分支事物B和C通过http，且B和C都有全局异常拦截，那么A就需要判断接收到的响应码显示的抛出异常实现全局事物的正常回滚。
             */
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }

        int a = 1 / 0;
        return R.ok();
    }

    @GetMapping("/test/mail")
    @ApiOperation(value = "测试邮件发送")
    public R<String> mail(@RequestParam String mail) {
        mailTemplate.sendMail("主题", "内容", new String[]{mail}, null, null, null);
        return R.ok(null, "邮件发送成功");
    }

    @PostMapping("/test/rabbit")
    @ApiOperation(value = "测试RabbitMQ消息")
    public R<String> rabbit(@RequestBody EmailDTO emailDTO) {
        mailProducer.testSendEmail(emailDTO);
        return R.ok(null, "发送消息成功");
    }

}
