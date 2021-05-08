package cn.yiidii.pigeon.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.util.HttpClientUtil;
import cn.yiidii.pigeon.common.core.util.dto.HttpClientResult;
import cn.yiidii.pigeon.common.ide.annotation.Ide;
import cn.yiidii.pigeon.common.mail.core.MailTemplate;
import cn.yiidii.pigeon.common.redis.core.RedisOps;
import cn.yiidii.pigeon.common.redis.lock.RedisDistributedLock;
import cn.yiidii.pigeon.common.security.service.PigeonUser;
import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.common.sftp.SftpConnection;
import cn.yiidii.pigeon.common.sftp.util.JschUtil;
import cn.yiidii.pigeon.demo.api.dto.EmailDTO;
import cn.yiidii.pigeon.demo.api.entity.Demo;
import cn.yiidii.pigeon.demo.mapper.DemoMapper;
import cn.yiidii.pigeon.demo.message.producer.IMailProducer;
import cn.yiidii.pigeon.kafka.channel.LogChannel;
import cn.yiidii.pigeon.kafka.constant.KafkaConstant;
import cn.yiidii.pigeon.log.annotation.Log;
import cn.yiidii.pigeon.rbac.api.dto.UserDTO;
import cn.yiidii.pigeon.rbac.api.feign.UserFeign;
import cn.yiidii.pigeon.rbac.api.form.UserForm;
import cn.yiidii.pigeon.rbac.api.vo.UserVO;
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
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: YiiDii Wang
 * @create: 2021-02-11 13:35
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@Api(tags = "Demo测试接口")
@EnableBinding(LogChannel.class)
public class TestDemoController {

    private final UserFeign userFeign;
    private final RedisOps redisOps;
    private final RedisDistributedLock redisDistributedLock;
    private final RedissonClient redissonClient;
    private final Environment env;
    private final DemoMapper demoMapper;
    private final MailTemplate mailTemplate;
    private final IMailProducer mailProducer;
    private final LogChannel logChannel;
    private final JschUtil jschUtil;

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
        Map<String, Object> map = new HashMap<>(16);
        map.put(key, value);
        redisOps.hmset("hash", map);
        return R.ok(null, JSONObject.toJSONString(map));
    }

    @GetMapping("/test/redisDistributedLock/lock")
    @ApiOperation(value = "测试redisDistributedLock  加锁")
    public R<String> redisDistributedLockLock(@RequestParam String key, @RequestParam String requestId, @RequestParam(required = false) Long expire) {
        Boolean lock = redisDistributedLock.tryLock(key, requestId, expire);
        String msg = StrUtil.format("获取分布式锁{}. key:{}, requestId: {}, expire: {}", lock ? "成功" : "失败", key, requestId);
        log.info(msg);
        return lock ? R.ok(null, msg) : R.failed(null, msg);
    }

    @GetMapping("/test/redisDistributedLock/unlock")
    @ApiOperation(value = "测试redisDistributedLock 释放锁")
    public R<String> redisDistributedLockUnLock(@RequestParam String key, @RequestParam String requestId) {
        Boolean success = redisDistributedLock.releaseLock(key, requestId);
        return success ? R.ok(null, StrUtil.format("释放分布式锁成功. key:{}, requestId: {}, expire: {}", key, requestId)) :
                R.failed(null, StrUtil.format("释放分布式锁失败. key:{}, requestId: {}, expire: {}", key, requestId));
    }

    @GetMapping("/test/redisson/lock")
    @ApiOperation(value = "测试redisson 加锁")
    @SneakyThrows
    public R<String> redissonLock(@RequestParam String key, @RequestParam(required = false) Long expire) {
        RLock rLock = redissonClient.getLock(key);
        boolean lock = rLock.tryLock(expire, TimeUnit.SECONDS);
        String msg = StrUtil.format("获取Redisson分布式锁{}. key:{}", lock ? "成功" : "失败", key);
        log.info(msg);
        return lock ? R.ok(null, msg) : R.failed(null, msg);
    }

    @GetMapping("/test/redisson/unLock")
    @ApiOperation(value = "测试redisson 释放锁")
    public R<String> redissonUnLock(@RequestParam String key) {
        String msg = StrUtil.format("开始释放Redisson分布式锁. key:{}", key);
        log.info(msg);
        RLock rLock = redissonClient.getLock(key);
        rLock.unlock();
        return R.ok(null, StrUtil.format("释放Redisson锁成功, key: {}", key));
    }

    @GetMapping("/test/redisson/all")
    @ApiOperation(value = "测试redisson all")
    @SneakyThrows
    public R<String> redissonLockAll(@RequestParam String key, @RequestParam(required = false) Long expire) {
        RLock rLock = redissonClient.getLock(key);

        boolean lock = rLock.tryLock(expire, TimeUnit.SECONDS);
        String msg = StrUtil.format("获取Redisson分布式锁{}. key:{}", lock ? "成功" : "失败", key);
        log.info(msg);

        TimeUnit.SECONDS.sleep(5L);

        rLock.unlock();
        msg = "释放Redisson分布式锁成功";
        log.info(msg);

        return R.ok(msg);
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
        UserForm form = UserForm.builder().username(String.valueOf(new Random().nextInt())).password("111").build();
        R<UserVO> userDTOR = userFeign.create(form);
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

    @GetMapping("/test/kafka")
    @ApiOperation(value = "测试kafka消息")
    public R<String> kafka(@RequestParam String message) {
        boolean send = logChannel.sendLogMessage().send(MessageBuilder.withPayload(message).build());
        return R.ok(null, StrUtil.format("发送kafka消息[{}]" + (send ? "成功" : "失败"), message));
    }

    @StreamListener(KafkaConstant.LOG_INPUT)
    public void handler(String message) {
        log.info(StrUtil.format("消费: {}, 接收时间: {}", message, new Date()));
    }


    @GetMapping("/test/log")
    @ApiOperation(value = "测试操作日志")
    @SneakyThrows
    @Log(content = "'name: ' + #name + ', 测试操作日志'", exception = "测试操作日志异常")
    public R<String> log(@RequestParam(required = false, defaultValue = "false") String name,
                         @RequestParam(required = false, defaultValue = "false") Boolean ex,
                         @RequestParam(required = false, defaultValue = "0") Long sec) {
        Assert.isTrue(!ex, "异常");
        TimeUnit.SECONDS.sleep(sec);
        return R.ok(null, StrUtil.format("测试操作日志成功"));
    }

    @GetMapping("/test/trace")
    @ApiOperation(value = "测试trace")
    @SneakyThrows
    public R<String> trace() {
        R<UserDTO> admin = userFeign.getUserDTOByUsername("admin");
        log.info("trace userDTO: {}", JSONObject.toJSON(admin));
        return R.ok(null, StrUtil.format("tarce test"));
    }

    @SneakyThrows
    @GetMapping("/test/ide")
    @ApiOperation(value = "测试接口幂等")
    @Ide
    public R ide(@RequestParam Long second) {
        TimeUnit.SECONDS.sleep(second);
        return R.ok(null, StrUtil.format("Ide test, second: {}s", second));
    }

    @PostMapping("/test/sftp")
    @ApiOperation(value = "测试sftp")
    public R sftp(@RequestBody SftpConnection connection) {
        String home = jschUtil.getHome(connection);
        return R.ok(null, StrUtil.format("home: {}", home));
    }

}
