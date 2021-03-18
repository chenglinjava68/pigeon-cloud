package cn.yiidii.pigeon.rbac.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-18 11:25
 */
@Data
@ApiModel("角色用户Form")
public class RoleUserForm {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色ID不能为空")
    Long roleId;

    @NotEmpty(message = "用户ID不能为空")
    List<Long> userIdList;

}
