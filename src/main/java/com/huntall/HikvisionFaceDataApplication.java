package com.huntall;

import com.huntall.common.constant.HikProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ HikProperties.class })
public class HikvisionFaceDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(HikvisionFaceDataApplication.class, args);
	}

}
