package cn.yiidii.pigeon.demo.message.producer.impl;

import cn.yiidii.pigeon.common.mail.core.MailTemplate;
import cn.yiidii.pigeon.common.rabbit.channel.PigeonSource;
import cn.yiidii.pigeon.demo.api.dto.EmailDTO;
import cn.yiidii.pigeon.demo.message.producer.IMailProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author: YiiDii Wang
 * @create: 2021-03-12 17:01
 */
@Service
@Slf4j
@EnableBinding(value = {PigeonSource.class})
@RequiredArgsConstructor
public class MailProducerImpl implements IMailProducer {

    private final PigeonSource source;

    @Override
    public void testSendEmail(EmailDTO emailDTO) {
        log.info("[Mail Producer] 发送消息: {}", emailDTO);
        source.emailOutput().send(MessageBuilder.withPayload(emailDTO).setHeader("x-delay", 10 * 1000L).build());
    }

}
