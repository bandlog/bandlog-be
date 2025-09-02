package net.effize.bandlog.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "net.effize.bandlog.bootstrap",
    "net.effize.bandlog.user",
    "net.effize.bandlog.team"
})
@EnableJpaRepositories(basePackages = {
    "net.effize.bandlog.user.domain.repository",
    "net.effize.bandlog.team.domain.repository"
})
@EntityScan(basePackages = {
    "net.effize.bandlog.user.domain.model",
    "net.effize.bandlog.team.domain.model"
})
public class BandlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandlogApplication.class, args);
    }
}