package br.com.shapeup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ShapeUpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShapeUpApplication.class, args);
    }

}
