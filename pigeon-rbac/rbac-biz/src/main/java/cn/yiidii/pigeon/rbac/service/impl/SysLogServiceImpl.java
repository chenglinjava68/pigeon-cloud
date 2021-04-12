package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.common.security.util.SecurityUtils;
import cn.yiidii.pigeon.rbac.api.entity.SysLog;
import cn.yiidii.pigeon.rbac.api.form.OptLogForm;
import cn.yiidii.pigeon.rbac.mapper.SysLogMapper;
import cn.yiidii.pigeon.rbac.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:25
 */
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

    @Override
    public void createOptLog(OptLogForm optLogForm) {
        SysLog sysLog = new SysLog();
        BeanUtils.copyProperties(optLogForm, sysLog);
        sysLog.setCreateTime(LocalDateTime.now());
        Long currUid = SecurityUtils.getUser().getId();
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setCreatedBy(currUid);
        sysLog.setUpdateTime(LocalDateTime.now());
        sysLog.setUpdatedBy(currUid);
        this.save(sysLog);
    }
}
