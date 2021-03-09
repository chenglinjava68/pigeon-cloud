package cn.yiidii.pigeon.file.strategy;

import cn.yiidii.pigeon.file.api.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件策略接口
 *
 * @author YiiDii Wang
 * @date 2021/3/9 21:52:13
 */
public interface FileStrategy {

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件对象
     */
    Attachment upload(MultipartFile file);

    /**
     * 文件删除
     *
     * @param bucketName
     * @param objectName
     * @return
     */
    boolean delete(String bucketName, String objectName);

}
