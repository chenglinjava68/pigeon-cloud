package cn.yiidii.pigeon.rbac.api.bo;

import cn.yiidii.pigeon.rbac.api.enumeration.ResourceType;
import lombok.Data;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-21 16:56
 */
@Data
public class ResourceBO {

    private Long resourceId;

    private ResourceType type;

}
