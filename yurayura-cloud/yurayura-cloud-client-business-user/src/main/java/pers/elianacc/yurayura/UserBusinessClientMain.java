package pers.elianacc.yurayura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UserBusinessClientMain {
    public static void main(String[] args) {
        SpringApplication.run(UserBusinessClientMain.class, args);
    }
}
