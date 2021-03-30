package cn.yiidii.pigeon.openapi.service;

import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.openapi.model.form.TelecomLoginForm;
import com.alibaba.fastjson.JSONObject;

/**
 * 运营商接口
 *
 * @author: YiiDii Wang
 * @create: 2021-03-07 16:55
 */
public interface ITelecomService {

    /**
     * 发送图片验证码（电信需要）
     *
     * @return
     */
    JSONObject getCaptcha();

    /**
     * 发送手机验证码
     *
     * @param telecomLoginForm
     * @return
     */
    String sendRandomNum(TelecomLoginForm telecomLoginForm);

    /**
     * 验证码登陆
     *
     * @param telecomLoginForm
     * @return
     */
    R randomLogin(TelecomLoginForm telecomLoginForm);

}
