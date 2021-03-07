package cn.yiidii.pigeon.openapi.service;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.openapi.model.form.TelecomLoginForm;

/**
 * 运营商接口
 *
 * @author: YiiDii Wang
 * @create: 2021-03-07 16:55
 */
public interface ITelecomService {

    /**
     * 发送验证码
     * @return
     */
    String sendRandomNum(TelecomLoginForm telecomLoginForm);

    /**
     * 验证码登陆
     * @param telecomLoginForm
     */
    R randomLogin(TelecomLoginForm telecomLoginForm) ;

}
