package pers.elianacc.yurayura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ComicBusinessClientMain {
    public static void main(String[] args) {
        SpringApplication.run(ComicBusinessClientMain.class, args);
    }
}
