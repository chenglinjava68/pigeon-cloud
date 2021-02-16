package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 用户类
 *
 * @author: YiiDii Wang
 * @create: 2021-01-08 23:37
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value = "User", description = "用户")
@AllArgsConstructor
public class User extends Entity<Long> {

    @NotBlank(message = "用户名不能为空")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "salt")
    private String salt;
    @TableField(value = "name")
    private String name;
    private String email;
    private String mobile;
    private String sex;
    private String avatar;
    @TableField(value = "`desc`")
    private String desc;
    private Integer pwdErrTimes;
    private LocalDateTime lastPwdErrTime;
    private LocalDateTime lastLoginTime;

}
