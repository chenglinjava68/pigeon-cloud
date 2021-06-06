package cn.yiidii.pigeon.demo.test;

import lombok.Builder;
import lombok.Data;

/**
 * 京东相关信息
 *
 * @author YiiDii Wang
 * @create 2021-05-31 13:40
 */
@Data
@Builder
public class JdInfo {

    private String sToken;
    private String guid;
    private String lsId;
    private String lsToken;
    private String cookies;
    private String oklToken;
    private String token;

}
