package kr.co.fastcampus.yanabada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YanabadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(YanabadaApplication.class, args);
	}

}
