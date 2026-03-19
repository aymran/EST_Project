package org.example.project_pfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjectPfeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectPfeApplication.class, args);
    }

}
