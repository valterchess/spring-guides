package io.spring.rsocketinit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RsocketInitApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "rpcserver");
		SpringApplication.run(RsocketInitApplication.class, args);
	}
}
