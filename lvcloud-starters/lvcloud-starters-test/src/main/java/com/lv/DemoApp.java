package com.lv;

import com.lv.cloud.jedis.annotation.EnableJedisCluster;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJedisCluster
public class DemoApp {
	
	public static void main(String[] args) {
        SpringApplication.run(DemoApp.class,args);
    }


}
