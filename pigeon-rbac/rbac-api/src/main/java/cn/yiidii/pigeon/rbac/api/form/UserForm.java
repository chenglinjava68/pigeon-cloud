package cn.yiidii.pigeon.rbac.api.form;

import cn.yiidii.pigeon.common.core.base.entity.SuperEntity;
import cn.yiidii.pigeon.rbac.api.enumeration.Sex;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 用户插入Form
 *
 * @author: YiiDii Wang
 * @create: 2021-03-17 21:19
 */
@SuperBuilder
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@ApiModel(value = "UserSaveForm", description = "用户保存表单")
public class UserForm {

    @NotNull(message = "用户ID不能为空", groups = SuperEntity.Update.class)
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = SuperEntity.Add.class)
    private String username;

    @NotBlank(message = "密码不能为空", groups = SuperEntity.Add.class)
    private String password;

    @NotBlank(message = "确认密码不能为空", groups = SuperEntity.Add.class)
    private String confirmPassword;

    @NotBlank(message = "昵称不能为空", groups = SuperEntity.Add.class)
    private String name;

    @Pattern(regexp = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$", message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$", message = "手机号格式不正确")
    private String mobile;

    private Sex sex = Sex.N;

    private String avatar;

    private String desc;

}
