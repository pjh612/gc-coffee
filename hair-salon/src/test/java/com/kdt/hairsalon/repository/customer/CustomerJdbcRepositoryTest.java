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
import org.springframework.dao.EmptyResultDataAccessException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class CustomerJdbcRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private static EmbeddedMysql embeddedMySql;

    private final Customer customerA = new Customer(
            UUID.randomUUID(),
            "park",
            "customerA@gmail.com",
            Gender.MAN, LocalDate.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
    );

    private final Customer customerB = new Customer(
            UUID.randomUUID(),
            "park",
            "customerB@gmail.com",
            Gender.WOMAN, LocalDate.now(),
            LocalDateTime.now(),
            LocalDateTime.now()
    );

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
    @Order(1)
    @DisplayName("Customer 정보 정상 추가")
    void insertTest() {
        //when
        customerRepository.insert(customerA);
        Optional<Customer> insertedCustomer = customerRepository.findById(customerA.getId());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customerA));
    }

    @Test
    @Order(2)
    @DisplayName("Customer끼리 이메일이 중복될 수 없다.")
    void insertWithDuplicatedEmailTest() {
        //given
        Customer duplicatedEmailCustomer = new Customer(
                UUID.randomUUID(),
                "lee",
                "customerA@gmail.com",
                Gender.MAN, LocalDate.now(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        //when, then
        assertThrows(DataAccessException.class, () -> customerRepository.insert(duplicatedEmailCustomer));

    }

    @Test
    @Order(3)
    @DisplayName("ID로 Customer 조회")
    void findById() {
        //when
        Optional<Customer> insertedCustomer = customerRepository.findById(customerA.getId());

        //then
        assertThat(insertedCustomer.isPresent(), is(true));
        assertThat(insertedCustomer.get(), samePropertyValuesAs(customerA));
    }

    @Test
    @Order(4)
    @DisplayName("동명이인 조회")
    void findByName() {
        //given
        customerRepository.insert(customerB);

        //when
        List<Customer> customers = customerRepository.findByName(customerA.getName());

        //then
        assertThat(customers.size(), is(2));
        assertThat(customers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(customerB)));
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 Customer 조회")
    void findByEmail() {
        Optional<Customer> foundCustomer = customerRepository.findByEmail(customerB.getEmail());

        assertThat(foundCustomer.isPresent(), is(true));
        assertThat(foundCustomer.get(), samePropertyValuesAs(customerB));
    }

    @Test
    @Order(6)
    @DisplayName("모든 Customer 조회 테스트")
    void findAll() {
        List<Customer> customers = customerRepository.findAll();

        //then
        assertThat(customers.size(), is(2));
        assertThat(customers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(customerB)));
    }

    @Test
    @Order(7)
    @DisplayName("Customer 삭제 테스트")
    void deleteByIdTest() {
        //when
        customerRepository.deleteById(customerA.getId());

        //then
        assertThrows(EmptyResultDataAccessException.class, () -> customerRepository.findById(customerA.getId()));
    }
}