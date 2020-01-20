package plus.planner.containerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ContainerService {

    public static void main(String[] args) {
        SpringApplication.run(ContainerService.class, args);
    }

}
