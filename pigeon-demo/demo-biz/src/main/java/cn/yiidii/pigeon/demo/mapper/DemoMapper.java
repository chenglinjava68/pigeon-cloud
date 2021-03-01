package cn.yiidii.pigeon.demo.mapper;

import cn.yiidii.pigeon.demo.api.entity.Demo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试mapper
 *
 * @author: YiiDii Wang
 * @create: 2021-02-28 14:43
 */
@Mapper
public interface DemoMapper extends BaseMapper<Demo> {

}
