package cn.yiidii.pigeon.rbac.service;

import cn.yiidii.pigeon.rbac.api.entity.SysLog;
import cn.yiidii.pigeon.rbac.api.form.OptLogForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 系统日志接口
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:24
 */
public interface ISysLogService extends IService<SysLog> {

    /**
     * 创建操作日志日志
     * @param optLogForm    操作日志
     */
    void createOptLog(OptLogForm optLogForm);

}
