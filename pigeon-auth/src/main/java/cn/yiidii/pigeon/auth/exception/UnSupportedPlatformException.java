package cn.yiidii.pigeon.auth.exception;

import cn.yiidii.pigeon.common.core.exception.BaseUncheckedException;
import cn.yiidii.pigeon.common.core.exception.code.ExceptionCode;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-28 17:34
 */
public class UnSupportedPlatformException extends BaseUncheckedException {
    
    public UnSupportedPlatformException(Throwable cause) {
        super(cause);
    }

    public UnSupportedPlatformException() {
        super(ExceptionCode.UNSUPPORTED_PLATFORM.getCode(), ExceptionCode.UNAUTHORIZED.getMsg());
    }

}
