package pers.elianacc.yurayura;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(basePackages = {"pers.elianacc.yurayura.dao"})
@EnableDiscoveryClient
public class SysMain {

    public static void main(String[] args) {
        SpringApplication.run(SysMain.class, args);
    }

}
