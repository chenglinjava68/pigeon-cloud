package cn.yiidii.pigeon.rbac.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.annotations.ApiModel;

/**
 * @author: YiiDii Wang
 * @create: 2021-01-16 00:37
 */
@Data
@ApiModel("资源DTO")
public class ResourceDTO {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "资源编码")
    private String code;
}
