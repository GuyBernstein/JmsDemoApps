package com.example.p2p;


import com.example.p2p.config.P2PConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(P2PConfig.class)
public class P2PDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(P2PDemoApplication.class, args);
    }
}
