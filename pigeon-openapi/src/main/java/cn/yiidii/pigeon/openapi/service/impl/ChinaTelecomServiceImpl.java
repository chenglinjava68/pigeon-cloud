package cn.yiidii.pigeon.openapi.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.yiidii.pigeon.common.core.base.R;
import cn.yiidii.pigeon.common.core.exception.BizException;
import cn.yiidii.pigeon.openapi.model.form.TelecomLoginForm;
import cn.yiidii.pigeon.openapi.service.ITelecomService;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 电信
 *
 * @author: YiiDii Wang
 * @create: 2021-03-30 10:41
 */
@Service("chinaTelecomService")
@RequiredArgsConstructor
public class ChinaTelecomServiceImpl implements ITelecomService {

    private static final String URL_VALIDATION_CODE = "https://appgo.189.cn:9200/query/getValidationCode";
    private static final String URL_LOGIN_RANDOM_CODE = "https://appgo.189.cn:9200/login/client/getLoginRandomCode";
    private static final String URL_LOGIN_NORMAL = "https://appgo.189.cn:9200/login/client/userLoginNormal";

    @Override
    public JSONObject getCaptcha() {
        // 参数
        JSONObject paramJo = new JSONObject();
        JSONObject headerInfos = new JSONObject();
        headerInfos.put("code", "getValidationCode");
        headerInfos.put("timestamp", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        headerInfos.put("shopId", "20002");
        headerInfos.put("source", "110003");
        headerInfos.put("sourcePassword", "Sid98s");
        headerInfos.put("broadToken", "");
        headerInfos.put("broadAccount", "");
        headerInfos.put("clientType", "#8.0.0#channel35#OnePlus ONEPLUS A6000#");
        paramJo.put("headerInfos", headerInfos);

        JSONObject content = new JSONObject();
        content.put("attach", "test");
        content.put("fieldData", new JSONObject());
        paramJo.put("content", content);

        // 请求
        HttpResponse resp = HttpRequest.post(URL_VALIDATION_CODE)
                .body(paramJo.toJSONString())
                .execute();

        // 解析响应
        if (resp.getStatus() != HttpStatus.HTTP_OK) {
            throw new BizException(String.format("获取图形验证码失败(%d)", resp.getStatus()));
        }

        String respStr = resp.body();
        JSONObject respBody = JSONObject.parseObject(respStr);
        JSONObject responseData = respBody.getJSONObject("responseData");
        if (Objects.isNull(responseData)) {
            throw new BizException("获取图形验证码失败");
        }
        return responseData.getJSONObject("data");
    }

    @Override
    public String sendRandomNum(TelecomLoginForm telecomLoginForm) {
        // 参数
        JSONObject paramJo = new JSONObject();
        JSONObject headerInfos = new JSONObject();
        headerInfos.put("code", "getLoginRandomCode");
        headerInfos.put("timestamp", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        headerInfos.put("shopId", "20002");
        headerInfos.put("source", "110003");
        headerInfos.put("sourcePassword", "Sid98s");
        headerInfos.put("broadToken", "");
        headerInfos.put("broadAccount", "");
        headerInfos.put("clientType", "#8.0.0#channel35#OnePlus ONEPLUS A6000#");
        paramJo.put("headerInfos", headerInfos);

        JSONObject content = new JSONObject();
        content.put("attach", "test");
        JSONObject fieldDataJo = new JSONObject();
        fieldDataJo.put("phoneNum", telecomLoginForm.getMobile());
        fieldDataJo.put("validationCode", telecomLoginForm.getUserContent());
        fieldDataJo.put("imsi", "");
        fieldDataJo.put("key", telecomLoginForm.getImageId());
        fieldDataJo.put("scene", "55");
        content.put("fieldData", fieldDataJo);
        paramJo.put("content", content);

        // 请求
        HttpResponse resp = HttpRequest.post(URL_LOGIN_RANDOM_CODE)
                .body(paramJo.toJSONString())
                .execute();

        // 解析响应
        if (resp.getStatus() != HttpStatus.HTTP_OK) {
            throw new BizException(String.format("发送短信验证码失败(%d)", resp.getStatus()));
        }

        String respStr = resp.body();
        JSONObject respBody = JSONObject.parseObject(respStr);
        System.out.println(respBody);
        JSONObject responseData = respBody.getJSONObject("responseData");
        if (Objects.isNull(responseData)) {
            throw new BizException("发送短信验证码失败");
        }

        return responseData.getString("resultDesc");
    }

    @Override
    public R randomLogin(TelecomLoginForm telecomLoginForm) {
        // 参数
        JSONObject paramJo = new JSONObject();
        JSONObject headerInfos = new JSONObject();
        headerInfos.put("code", "userLoginNormal");
        headerInfos.put("timestamp", DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT));
        headerInfos.put("broadAccount", "");
        headerInfos.put("broadToken", "");
        headerInfos.put("clientType", "#8.0.0#channel35#OnePlus ONEPLUS A6000#");
        headerInfos.put("shopId", "20002");
        headerInfos.put("source", "110003");
        headerInfos.put("sourcePassword", "Sid98s");
        headerInfos.put("token", "");
        headerInfos.put("userLoginName", telecomLoginForm.getMobile());
        paramJo.put("headerInfos", headerInfos);

        JSONObject content = new JSONObject();
        content.put("attach", "test");
        JSONObject fieldDataJo = new JSONObject();
        fieldDataJo.put("loginType", 2);
        fieldDataJo.put("accountType", "");
        fieldDataJo.put("loginAuthCipherAsymmertric", "mJXbZVdTxQEbbcYUEbgotwD+wozIpw0sQHKuJHK0kpSWyiFlC5kfpcnB6DYni32O/Va2u4BLcgWT80eHTOLm/3kCCbiYIgLKnh1ut7XNs9TQyu+qF01uRdVmPlyKjv5gP+1rsbgP0zTwPsRLms+olLgXQmuPMFCQxbguN7dZxKo=");
        fieldDataJo.put("deviceUid", "");
        fieldDataJo.put("phoneNum", telecomLoginForm.getMobile());
        fieldDataJo.put("isChinatelecom", "1");
        fieldDataJo.put("systemVersion", "10");
        fieldDataJo.put("androidId", "8d54924078257795");
        fieldDataJo.put("loginAuthCipher", "");
        fieldDataJo.put("authentication", telecomLoginForm.getPassword());
        content.put("fieldData", fieldDataJo);
        paramJo.put("content", content);

        // 请求
        HttpResponse resp = HttpRequest.post(URL_LOGIN_NORMAL)
                .body(paramJo.toJSONString())
                .execute();

        // 解析响应
        if (resp.getStatus() != HttpStatus.HTTP_OK) {
            throw new BizException(String.format("登陆失败(%d)", resp.getStatus()));
        }

        String respStr = resp.body();
        JSONObject respBody = JSONObject.parseObject(respStr);
        System.out.println(respBody);
        JSONObject responseData = respBody.getJSONObject("responseData");
        if (Objects.isNull(responseData)) {
            throw new BizException("登陆失败");
        }

        JSONObject resultJo = new JSONObject();
        resultJo.put("chinaTelecomResp", respBody);
        resultJo.put("cookieStr", resp.getCookieStr());
        return R.ok(resultJo, "登陆成功");
    }

}
