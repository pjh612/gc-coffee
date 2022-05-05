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
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMySql;

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
    }

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("Customer 정보 정상 추가")
    void insertTest() {
        //given
        Customer customer = new Customer(
                UUID.randomUUID(),
                "customerA",
                "customerA@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now());

        //when
        customerRepository.insert(customer);
        Optional<Customer> insertedCustomer = customerRepository.findById(customer.getId());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customer));
    }

    @Test
    @DisplayName("Customer끼리 이메일이 중복될 수 없다.")
    void insertWithDuplicatedEmailTest() {
        //given
        Customer customer = new Customer(
                UUID.randomUUID(),
                "customerB",
                "customerB@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Customer duplicatedEmailCustomer = new Customer(
                UUID.randomUUID(),
                "customerB",
                "customerB@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        customerRepository.insert(customer);

        //when, then
        assertThrows(DataAccessException.class, () -> customerRepository.insert(duplicatedEmailCustomer));
    }

    @Test
    @DisplayName("Customer 삭제 테스트")
    void deleteByIdTest() {
        //given
        Customer customer = new Customer(
                UUID.randomUUID(),
                "customerG",
                "customerG@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        customerRepository.insert(customer);

        //when
        customerRepository.deleteById(customer.getId());
        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());

        //then
        assertThat(foundCustomer.isEmpty(), is(true));
    }
}