package cn.yiidii.pigeon.rbac.api.entity;

import cn.yiidii.pigeon.common.core.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统日志
 *
 * @author: YiiDii Wang
 * @create: 2021-04-12 21:01
 */
@Data
@TableName("sys_log")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysLog", description = "系统日志")
public class SysLog extends Entity<Long> {

    private static final long serialVersionUID = 1L;

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
    private String operation;

    /**
     * 执行方法
     */
    @ApiModelProperty(value = "执行方法")
    private String method;

    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
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
