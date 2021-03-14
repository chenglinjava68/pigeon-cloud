package cn.yiidii.pigeon.file.service.impl;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.file.api.entity.Attachment;
import cn.yiidii.pigeon.file.mapper.AttachmentMapper;
import cn.yiidii.pigeon.file.service.IAttachmentService;
import cn.yiidii.pigeon.file.strategy.FileStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 *
 *
 * @author YiiDii Wang
 * @date 2021/3/9 23:22:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

    private final FileStrategy fileStrategy;
    private final AttachmentMapper attachmentMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Attachment upload(MultipartFile multipartFile) {
        Attachment attachment = fileStrategy.upload(multipartFile);
        attachment.setCreateTime(LocalDateTime.now());
        attachment.setUpdateTime(LocalDateTime.now());
        attachmentMapper.insert(attachment);
        return attachment;
    }

    @Override
    public IPage<Attachment> list(BaseSearchParam searchParam) {
        LambdaQueryWrapper<Attachment> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.between(StringUtils.isNotBlank(searchParam.getStartTime()), Attachment::getCreateTime, searchParam.getStartTime(), searchParam.getEndTime());
        boolean isKeyword = StringUtils.isNotBlank(searchParam.getKeyword());
        queryWrapper.like(isKeyword, Attachment::getFilename, searchParam.getKeyword()).or(isKeyword)
                .like(isKeyword, Attachment::getId, searchParam.getKeyword());

        // 根据排序字段进行排序
        if (StringUtils.isNotBlank(searchParam.getOrderBy())) {
        }

        // 分页查询
        Page<Attachment> page = new Page<>(searchParam.getCurrent(), searchParam.getSize());
        return this.baseMapper.selectPage(page, queryWrapper);
    }

}
