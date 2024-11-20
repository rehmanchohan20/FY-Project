package com.rehman.elearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.rehman.elearning.config.RsaKeyConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
@EnableTransactionManagement
@EnableJpaAuditing
public class ElearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElearningApplication.class, args);
	}
}
