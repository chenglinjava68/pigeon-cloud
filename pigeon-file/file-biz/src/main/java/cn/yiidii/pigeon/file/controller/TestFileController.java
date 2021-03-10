package cn.yiidii.pigeon.file.controller;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.file.api.entity.Attachment;
import cn.yiidii.pigeon.file.properties.OssProperties;
import cn.yiidii.pigeon.file.service.IAttachmentService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private final OssProperties ossProperties;


    @GetMapping("/test/hello")
    @ApiOperation(value = "hello")
    public R hello() {
        return R.ok("hello file");
    }

    @PostMapping("/test/uploadFile")
    @ApiOperation(value = "文件上传")
    public R<Attachment> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        Attachment attachment = attachmentService.upload(file);
        log.info("上传文件：{}", JSONObject.toJSON(attachment));
        return R.ok(attachment, "上传成功");
    }

    @GetMapping("/test/attachment")
    @ApiOperation(value = "获取文件")
    public R attachment() {
        List<Attachment> attachmentList = attachmentService.lambdaQuery().list();
        JSONObject jo = new JSONObject();
        jo.put("customDomain", ossProperties.getCustomDomain());
        jo.put("record", attachmentList);
        return R.ok(jo);
    }

}
