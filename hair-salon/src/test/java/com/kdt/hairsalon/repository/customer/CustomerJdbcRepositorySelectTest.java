package com.kdt.hairsalon.repository.customer;

import com.kdt.hairsalon.model.Customer;
import com.kdt.hairsalon.model.Gender;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class CustomerJdbcRepositorySelectTest {
    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMySql;

    Customer customerA = new Customer(
            UUID.randomUUID(),
            "customerA",
            "customerA@gmail.com",
            Gender.MAN, LocalDate.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "");

    Customer customerB = new Customer(
            UUID.randomUUID(),
            "customerB",
            "customerB@gmail.com",
            Gender.MAN, LocalDate.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "");

    Customer customerC = new Customer(
            UUID.randomUUID(),
            "customerC",
            "customerC@gmail.com",
            Gender.WOMAN, LocalDate.now(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            "");

    @BeforeAll
    void setup() {
        MysqldConfig config = aMysqldConfig(Version.v5_7_latest)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .withTimeZone("Asia/Seoul")
                .build();
        embeddedMySql = anEmbeddedMysql(config)
                .addSchema("test-hair_salon", classPathScript("schema.sql"))
                .start();
        customerRepository.insert(customerA);
        customerRepository.insert(customerB);
        customerRepository.insert(customerC);
    }

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("ID로 Customer 조회")
    void findById() {
        //when
        Optional<Customer> insertedCustomer = customerRepository.findById(customerA.getId());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customerA));
    }

    @Test
    void findByName() {
        Customer sameNameCustomer = new Customer(
                UUID.randomUUID(),
                "customerA",
                "customerD@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                "");
        customerRepository.insert(sameNameCustomer);

        //when
        List<Customer> customers = customerRepository.findByName(sameNameCustomer.getName());

        //then
        assertThat(customers.size(), is(2));
        assertThat(customers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(sameNameCustomer)));
    }

    @Test
    @DisplayName("이메일로 Customer 조회")
    void findByEmail() {
        //when
        Optional<Customer> foundCustomer = customerRepository.findByEmail(customerA.getEmail());

        //then
        assertThat(foundCustomer.isPresent(), is(true));
        assertThat(foundCustomer.get(), samePropertyValuesAs(customerA));
    }

    @Test
    @DisplayName("모든 Customer 조회 테스트")
    void findAll() {
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.size(), is(3));
        assertThat(customers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(customerB), samePropertyValuesAs(customerC)));
    }
}
