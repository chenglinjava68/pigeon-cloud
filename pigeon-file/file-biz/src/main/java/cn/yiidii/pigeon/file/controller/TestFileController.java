package cn.yiidii.pigeon.file.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.file.api.entity.Attachment;
import cn.yiidii.pigeon.file.service.IAttachmentService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 测试
 *
 * @author: YiiDii Wang
 * @create: 2021-03-09 23:15
 */
@Slf4j
@RestController
@Api(tags = "测试文件上传")
@RequiredArgsConstructor
public class TestFileController {

    private final IAttachmentService attachmentService;

    @GetMapping("/test/hello")
    @ApiOperation(value = "hello")
    public R hello() {
        return R.ok("hello file");
    }

    @PostMapping("/test/uploadFile")
    @ApiOperation(value = "文件上传")
    public R uploadFile(@RequestParam(value = "file") MultipartFile file) {
        Attachment attachment = attachmentService.upload(file);
        log.info("上传文件：{}", JSONObject.toJSON(attachment));
        return R.ok("hello file");
    }

}
