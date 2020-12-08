package edu.bpm.carbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// TODO: Not config yet
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class CarbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarbonApplication.class, args);
    }

}
