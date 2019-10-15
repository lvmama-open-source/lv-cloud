package com.lv;

import com.lv.cloud.jedis.annotation.EnableJedisCluster;
import com.lv.cloud.kafka.annotation.EnableLvKafka;
import com.lv.cloud.test.Foo1;
import com.lv.cloud.test.Foo2;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableJedisCluster
@EnableLvKafka
public class DemoApp {
	
	public static void main(String[] args) {
        SpringApplication.run(DemoApp.class,args);
    }

    private final Logger logger = LoggerFactory.getLogger(DemoApp.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(id = "fooGroup0", topics = "topic21")
    public void listen21(Foo2 foo1) throws IOException {
        logger.info("Received: " + foo1);
        kafkaTemplate.send("topic3", foo1.getFoo().toUpperCase());
    }

    @Bean
    public NewTopic topic21() {
        return new NewTopic("topic21",1, (short) 1);
    }

    @KafkaListener(id = "fooGroup1", topics = "topic2")
    public void listen1(List<Foo1> foos) throws IOException {
        logger.info("Received: " + foos);
        foos.forEach(f -> kafkaTemplate.send("topic3", f.getFoo().toUpperCase()));
        logger.info("Messages sent, hit Enter to commit tx");
        System.in.read();
    }

    @KafkaListener(id = "fooGroup2", topics = "topic3")
    public void listen2(List<String> in) {
        logger.info("Received: " + in);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("topic2",1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("topic3",1, (short) 1);
    }

}
