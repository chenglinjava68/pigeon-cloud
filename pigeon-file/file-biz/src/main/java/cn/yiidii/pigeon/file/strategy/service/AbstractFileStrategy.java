package cn.yiidii.pigeon.file.strategy.service;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.file.api.entity.Attachment;
import cn.yiidii.pigeon.file.properties.OssProperties;
import cn.yiidii.pigeon.file.strategy.FileStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


/**
 * 文件抽象策略 处理类
 *
 * @author zuihou
 * @date 2019/06/17
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractFileStrategy implements FileStrategy {

    private static final String FILE_SPLIT = ".";
    protected final OssProperties fileProperties;

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @return 附件
     */
    @Override
    public Attachment upload(MultipartFile multipartFile) {
        try {
            if (!StrUtil.contains(multipartFile.getOriginalFilename(), FILE_SPLIT)) {
                throw new BizException("缺少后缀名");
            }
            // url: /date/id-name.suffix
            String originalFilename = multipartFile.getOriginalFilename();
            String url =  LocalDateTimeUtil.formatNormal(LocalDate.now()).concat("/").concat(IdUtil.simpleUUID()).concat("-").concat(originalFilename);
            Attachment file = Attachment.builder()
                    .url(url)
                    .filename(originalFilename)
                    .suffix(FileUtil.getSuffix(originalFilename))
                    .size(multipartFile.getSize())
                    .build();
            practicalUploadFile(file, multipartFile);
            return file;
        } catch (Exception e) {
            log.error("ex=", e);
            throw new BizException("文件上传失败");
        }
    }

    /**
     * 具体类型执行上传操作
     *
     * @param file          附件
     * @param multipartFile 文件
     * @throws Exception 异常
     */
    protected abstract void practicalUploadFile(Attachment file, MultipartFile multipartFile) throws Exception;


    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    @Override
    public abstract boolean delete(String bucketName, String objectName);

}
