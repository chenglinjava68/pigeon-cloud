package cn.yiidii.pigeon.rbac.api.vo;

import cn.yiidii.pigeon.common.core.base.enumeration.Status;
import cn.yiidii.pigeon.rbac.api.enumeration.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户VO
 *
 * @author: YiiDii Wang
 * @create: 2021-03-15 13:17
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "User", description = "用户")
@AllArgsConstructor
public class UserVO implements Serializable {

    @ApiModelProperty(value = "主键")
    protected Long id;

    private String username;

    private String name;

    private String email;

    private String mobile;

    private Sex sex;

    private String avatar;

    private String desc;

    private Integer pwdErrTimes;

    private LocalDateTime lastPwdErrTime;

    private LocalDateTime lastLoginTime;

    private Status status;

    @ApiModelProperty(value = "最后修改时间")
    protected LocalDateTime updateTime;

    @ApiModelProperty(value = "最后修改人ID")
    protected Long updatedBy;

    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    protected Long createdBy;

}
