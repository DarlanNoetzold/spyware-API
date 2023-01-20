package tech.noetzold.spyware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableCaching
@SpringBootApplication
public class SpywareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpywareApplication.class, args);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
