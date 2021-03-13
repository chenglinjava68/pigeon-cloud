package cn.yiidii.pigeon.demo.message.producer;

import cn.yiidii.pigeon.demo.api.dto.EmailDTO;

/**
 * 测试邮件生产者
 *
 * @author: YiiDii Wang
 * @create: 2021-03-12 16:33
 */
public interface IMailProducer {

    /**
     * 测试发送邮件
     * @param emailDTO
     */
    void testSendEmail(EmailDTO emailDTO);

}
