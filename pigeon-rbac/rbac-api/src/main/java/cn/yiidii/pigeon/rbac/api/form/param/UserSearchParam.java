package cn.yiidii.pigeon.rbac.api.form.param;

import cn.yiidii.pigeon.common.core.base.BaseSearchParam;
import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: YiiDii Wang
 * @create: 2021-04-02 23:27
 */
@Data
public class UserSearchParam extends BaseSearchParam {

    @NotNull(message = "组织ID不能为空")
    private Long orgId;

}
