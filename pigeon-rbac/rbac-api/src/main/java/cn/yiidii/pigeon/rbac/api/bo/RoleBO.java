package cn.yiidii.pigeon.rbac.api.bo;

import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import lombok.Data;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-21 17:03
 */
@Data
public class RoleBO {

    private String id;

    private String code;

    private String name;

    private String desc;

}
