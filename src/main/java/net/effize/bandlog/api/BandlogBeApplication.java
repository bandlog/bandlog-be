package net.effize.bandlog.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "net.effize.bandlog")
public class BandlogBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BandlogBeApplication.class, args);
    }

}
