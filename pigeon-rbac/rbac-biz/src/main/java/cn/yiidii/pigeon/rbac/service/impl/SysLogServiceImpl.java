package cn.yiidii.pigeon.rbac.service.impl;

import cn.yiidii.pigeon.common.security.service.PigeonUser;
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
import java.util.Objects;

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
        // 时间
        sysLog.setCreateTime(LocalDateTime.now());
        sysLog.setUpdateTime(LocalDateTime.now());

        // 创建人
        PigeonUser user = SecurityUtils.getUser();
        if (Objects.nonNull(user)) {
            Long currUid = user.getId();
            sysLog.setCreatedBy(currUid);
            sysLog.setUpdatedBy(currUid);
        }
        this.save(sysLog);
    }
}
