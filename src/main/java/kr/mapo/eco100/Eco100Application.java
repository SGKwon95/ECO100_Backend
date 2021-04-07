package kr.mapo.eco100;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Eco100Application {

    public static void main(String[] args) {
        SpringApplication.run(Eco100Application.class, args);
    }

}
