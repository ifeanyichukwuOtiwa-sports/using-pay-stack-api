package io.wtech.usingpaystack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(
        classes = {
                UsingPaystackApplication.class,
                UsingPaystackApplicationTests.TestConfig.class
        }
)
class UsingPaystackApplicationTests {

    @Test
    void contextLoads() {
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @ServiceConnection
        static MySQLContainer<?> mysqlContainer() {
            return new MySQLContainer<>(DockerImageName.parse("mysql:8.0.29"));
        }
    }

}
