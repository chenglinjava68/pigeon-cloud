package cn.yiidii.pigeon.demo.message.consumer;

import cn.yiidii.pigeon.common.mail.core.MailTemplate;
import cn.yiidii.pigeon.common.rabbit.channel.PigeonSink;
import cn.yiidii.pigeon.common.rabbit.constant.ChannelConstant;
import cn.yiidii.pigeon.demo.api.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 测试邮件消费
 *
 * @author: YiiDii Wang
 * @create: 2021-03-12 17:08
 */
@Slf4j
@Component
@RequiredArgsConstructor
@EnableBinding(value = {PigeonSink.class})
public class TestMailConsumer {

    private final MailTemplate mailTemplate;

    @StreamListener(ChannelConstant.EMAIL_TEST_INPUT)
    public void testMailConsume(@Payload EmailDTO emailDTO) {
        log.info("[Mail Consumer] 消费消息: {}", emailDTO);
        mailTemplate.sendSimpleTextMail(emailDTO.getSubject(), emailDTO.getContent(), emailDTO.getToWho(), null, null);
    }
}
