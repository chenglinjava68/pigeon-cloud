package cn.yiidii.pigeon.file.controller;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.constant.StringPool;
import cn.yiidii.pigeon.file.api.entity.Attachment;
import cn.yiidii.pigeon.file.properties.OssProperties;
import cn.yiidii.pigeon.file.service.IAttachmentService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件接口
 *
 * @author: YiiDii Wang
 * @create: 2021-03-09 23:15
 */
@Slf4j
@RestController
@RequestMapping("attachment")
@Api(tags = "文件接口")
@RequiredArgsConstructor
public class FileController {

    private final IAttachmentService attachmentService;
    private final OssProperties ossProperties;

    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public R<Attachment> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        Attachment attachment = attachmentService.upload(file);
        return R.ok(attachment, "上传成功");
    }

    @GetMapping("/list")
    @ApiOperation(value = "文件列表")
    public R attachment(BaseSearchParam searchParam) {
        IPage<Attachment> pageData = attachmentService.list(searchParam);
        List<Attachment> records = pageData.getRecords();
        String customDomain = ossProperties.getCustomDomain();
        records.forEach(record->{
            record.setUrl(customDomain + StringPool.SLASH + record.getUrl());
        });
        return R.ok(pageData);
    }

}
