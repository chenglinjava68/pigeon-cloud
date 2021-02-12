package cn.yiidii.pigeon.rbac.mapper;


import cn.yiidii.pigeon.rbac.api.entity.OauthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  客户端详情
 *
 * @author YiiDii Wang
 * @date 2021/1/17 12:59:14
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {
}
