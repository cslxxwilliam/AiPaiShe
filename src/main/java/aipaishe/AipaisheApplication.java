package aipaishe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by hillmon on 18/9/2016.
 */
@SpringBootApplication
@EnableScheduling
public class AipaisheApplication {

    public static void main(String[] args) {
        SpringApplication.run(AipaisheApplication.class);
    }
}
