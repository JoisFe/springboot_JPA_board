package com.example.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Slf4j
@SpringBootApplication
@PropertySource(value = {"classpath:db.properties", "file:/data/properties/db.properies"}, ignoreResourceNotFound = true)
//@MapperScan(basePackages = "com.example.board")
public class BoardApplication {

	public static void main(String[] args) {

		SpringApplication.run(BoardApplication.class, args);

	}

}
