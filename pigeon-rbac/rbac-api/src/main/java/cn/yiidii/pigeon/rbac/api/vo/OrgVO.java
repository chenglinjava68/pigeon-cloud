package cn.yiidii.pigeon.rbac.api.vo;

import cn.yiidii.pigeon.common.core.base.entity.TreeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: YiiDii Wang
 * @create: 2021-04-03 16:40
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgVO extends TreeEntity<OrgVO, Long> {

    @ApiModelProperty(value = "标签")
    private String label;

}
