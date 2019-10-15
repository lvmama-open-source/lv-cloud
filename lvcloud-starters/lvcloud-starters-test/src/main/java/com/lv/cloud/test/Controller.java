package com.lv.cloud.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.AbstractJavaTypeMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoyulin
 * @description
 * @date 2019-10-10
 */
@RestController
public class Controller {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping(path = "/send/foos/{what}")
    public void sendFoo(@PathVariable String what) {
        this.template.executeInTransaction(kafkaTemplate -> {
            StringUtils.commaDelimitedListToSet(what).stream()
                    .map(s -> new Foo1(s))
                    .forEach(foo -> kafkaTemplate.send("topic2", foo));
            return true;
        });
    }

    @GetMapping(path = "/send1/foos/{what}")
    public void sendSingleFoo(@PathVariable String what) {
        this.template.executeInTransaction(kafkaTemplate -> {
            kafkaTemplate.send("topic21", new Foo1(what));
            return null;
        });
    }

    private Message<Foo1> createMessage(String topic, Foo1 foo1){
        Map<String, Object> headers = new HashMap<>();
        headers.put("kafka_topic", topic);
        headers.put(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME, foo1.getClass().getName());
        Message<Foo1> message = new GenericMessage<Foo1>(foo1, headers);
        return message;
    }

}
