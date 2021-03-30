package cn.yiidii.pigeon.openapi.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
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

    @Override
    public JSONObject getCaptcha() {
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

        HttpResponse resp = HttpRequest.post(URL_VALIDATION_CODE)
                .body(paramJo.toJSONString())
                .execute();

        if (resp.getStatus() != 200) {
            throw new BizException(String.format("获取图形验证码失败(%d)", resp.getStatus()));
        }

        String respStr = resp.body();
        JSONObject respBody = JSONObject.parseObject(respStr);
        JSONObject responseData = respBody.getJSONObject("responseData");
        if (Objects.isNull(responseData)) {
            throw new BizException("发送验证码失败");
        }
        return responseData.getJSONObject("data");
    }

    @Override
    public String sendRandomNum(TelecomLoginForm telecomLoginForm) {
        return null;
    }

    @Override
    public R randomLogin(TelecomLoginForm telecomLoginForm) {
        return null;
    }

}
