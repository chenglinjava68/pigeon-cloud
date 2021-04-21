package cn.yiidii.pigeon.rbac.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Map;

/**
 * 系统日志DTO
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:29
 */
@Data
@ApiModel("系统日志表单")
@SuperBuilder
@EqualsAndHashCode
public class OptLogForm implements Serializable {

    private static final long serialVersionUID = 1L;

    public OptLogForm() {
    }

    /**
     * 日志类型
     */
    @ApiModelProperty(value = "日志类型")
    private String type;

    /**
     * 跟踪ID
     */
    @ApiModelProperty(value = "跟踪ID")
    private String traceId;

    /**
     * 日志标题
     */
    @ApiModelProperty(value = "日志标题")
    private String title;

    /**
     * 操作内容
     */
    @ApiModelProperty(value = "操作内容")
    @NotBlank(message = "操作内容不能为空")
    private String operation;

    /**
     * 执行方法
     */
    @ApiModelProperty(value = "执行方法")
    @NotBlank(message = "执行方法不能为空")
    private String method;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    @NotBlank(message = "请求路径不能为空")
    private String url;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * ip地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;

    /**
     * 耗时
     */
    @ApiModelProperty(value = "耗时")
    private Long executeTime;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    private String location;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息")
    private String exception;

}
