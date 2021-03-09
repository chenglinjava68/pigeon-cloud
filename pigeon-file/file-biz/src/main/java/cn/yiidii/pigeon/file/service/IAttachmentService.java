package cn.yiidii.pigeon.file.service;

import cn.yiidii.pigeon.file.api.entity.Attachment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 业务接口
 * 附件
 * </p>
 *
 * @author zuihou
 * @date 2019-06-24
 */
public interface IAttachmentService extends IService<Attachment> {
    /**
     * 上传附件
     *
     * @param file     文件
     * @return 附件
     */
    Attachment upload(MultipartFile file);

}
