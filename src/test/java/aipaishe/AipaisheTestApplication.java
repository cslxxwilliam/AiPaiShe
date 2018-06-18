package aipaishe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by hillmon on 13/11/2016.
 */
@SpringBootApplication
@EnableScheduling
public class AipaisheTestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AipaisheTestApplication.class);
    }
}
