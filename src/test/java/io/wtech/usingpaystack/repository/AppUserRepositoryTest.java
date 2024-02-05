package io.wtech.usingpaystack.repository;

import io.wtech.usingpaystack.model.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AppUserRepositoryTest extends BasePersistenceTest {

    @Autowired
    private AppUserRepository sut;

    @Autowired
    private JdbcClient jdbcClient;


    @Test
    void save() {
        final AppUser user = AppUser.builder()
                .address("address")
                .name("name")
                .username("username")
                .build();

        final AppUser appUser = sut.saveAndFlush(user);

        assertThat(appUser)
                .satisfies(x -> {
                    assertThat(x.getName()).isEqualTo(user.getName());
                    assertThat(x.getUsername()).isEqualTo(user.getUsername());
                    assertThat(x.getAddress()).isEqualTo(user.getAddress());
                    assertThat(x.getPayments()).isEmpty();
                    assertThat(x.getCreatedOn()).isEqualToIgnoringSeconds(LocalDateTime.now());
                });
    }


    @Test
    void save_with_payments() {
        final int num = jdbcClient.sql(
                """
                        INSERT INTO app_user(id, username, name, address, created_on)
                        VALUES (UUID_TO_BIN('64837b69-1f73-4bb4-b9e1-6f3e98469d4d'),'username','name','address',NOW());
                        """
        ).update();
        final int num2 = jdbcClient.sql(
                """
                        INSERT INTO paystack_payment (id, user_id, reference, amount, paid_at, created_at, channel, currency, ip_address, pricing_plan_type, created_on, gateway_response)
                        VALUES (UUID_TO_BIN(UUID()),UUID_TO_BIN('64837b69-1f73-4bb4-b9e1-6f3e98469d4d'),'reference',10000.05,'paid_at','created_at','channel','Dollars','10.192.169.101','BASIC',NOW(), 'response')
                        """
        ).update();

        final UUID uuid = UUID.fromString("64837b69-1f73-4bb4-b9e1-6f3e98469d4d");

        final Optional<AppUser> appUserOptional = sut.findById(uuid);

        assertThat(num).isEqualTo(num2).isOne();

        assertThat(appUserOptional).isNotEmpty()
                .hasValueSatisfying(appUser -> {
                    assertThat(appUser)
                            .satisfies(x -> {
                                assertThat(x.getName()).isEqualTo("name");
                                assertThat(x.getUsername()).isEqualTo("username");
                                assertThat(x.getAddress()).isEqualTo("address");
                                assertThat(x.getPayments())
                                        .first()
                                        .satisfies(p -> {
                                            assertThat(p.getCreatedOn()).isEqualToIgnoringSeconds(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime());
                                            assertThat(p.getUser().getId()).isEqualTo(x.getId()).isEqualTo(uuid);
                                        });
                                assertThat(x.getCreatedOn()).isEqualToIgnoringSeconds(ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime());
                            });
                });
    }

    }