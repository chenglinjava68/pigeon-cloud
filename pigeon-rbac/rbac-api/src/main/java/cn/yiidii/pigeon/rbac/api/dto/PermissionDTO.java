package cn.yiidii.pigeon.rbac.api.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 权限DTO
 *
 * @author: YiiDii Wang
 * @create: 2021-03-21 16:31
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "PermissionDTO", description = "权限DTO")
@AllArgsConstructor
public class PermissionDTO {

    @ApiModelProperty(value = "权限编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "权限描述")
    @TableField(value = "`desc`")
    private String desc;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
