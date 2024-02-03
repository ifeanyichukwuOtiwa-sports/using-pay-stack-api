package io.wtech.usingpaystack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(
        classes = {
                UsingPaystackApplication.class,
                UsingPaystackApplicationTests.TestConfig.class
        }
)
class UsingPaystackApplicationTests {
    @Autowired
    private UsingPaystackApplication application;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();

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
