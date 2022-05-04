package com.kdt.hairsalon.service.customer;

import com.kdt.hairsalon.model.Gender;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DefaultCustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    private static EmbeddedMysql embeddedMySql;

    @BeforeAll
    static void setup() {
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

    @Test
    @DisplayName("Customer 생성 테스트")
    void createTest() {
        //when
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());
        CustomerDto foundCustomer = customerService.findById(customerA.getId());

        //then
        assertThat(foundCustomer, samePropertyValuesAs(customerA));
    }

    @Test
    @DisplayName("중복된 email로 Customer 생성은 실패해야 한다.")
    void createCustomerWithDuplicatedEmailFailTest() {
        //givne
        CustomerDto customerA = customerService.create("customerA", "customer@gmail.com", Gender.MAN, LocalDate.now());

        //when, then
        assertThrows(DuplicateKeyException.class, () -> customerService.create("customerB", "customer@gmail.com", Gender.WOMAN, LocalDate.now()));
    }

    @Test
    @DisplayName("Customer ID로 조회 테스트")
    void findByIdTest() {
        //given
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());

        //when
        CustomerDto foundCustomer = customerService.findById(customerA.getId());

        //then
        assertThat(foundCustomer, samePropertyValuesAs(customerA));
    }

    @Test
    @DisplayName("동명이인의 Customer 조회 테스트")
    void findByNameTest() {
        //given
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());
        CustomerDto sameNameWithCustomerACustomer = customerService.create("customerA", "anotherCustomer@gmail.com", Gender.MAN, LocalDate.now());
        //when
        List<CustomerDto> sameNameCustomers = customerService.findByName("customerA");

        //then
        assertThat(sameNameCustomers.size(), is(2));
        assertThat(sameNameCustomers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(sameNameWithCustomerACustomer)));
    }

    @Test
    @DisplayName("Customer Email로 조회 테스트")
    void findByEmailTest() {
        //given
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());

        //when
        CustomerDto foundCustomer = customerService.findByEmail("customera@gmail.com");

        //then
        assertThat(foundCustomer, samePropertyValuesAs(customerA));
    }

    @Test
    @DisplayName("Customer 전체 조회 테스트")
    void findAllTest() {
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());
        CustomerDto customerB = customerService.create("customerB", "cusotmerb@gmail.com", Gender.WOMAN, LocalDate.now());

        //when
        List<CustomerDto> foundCustomers = customerService.findAll();

        //then
        assertThat(foundCustomers.size(), is(2));
        assertThat(foundCustomers, containsInAnyOrder(samePropertyValuesAs(customerA), samePropertyValuesAs(customerB)));
    }

    @Test
    @DisplayName("Customer 삭제 테스트")
    void deleteById() {
        //given
        CustomerDto customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());

        //when
        customerService.deleteById(customerA.getId());

        //then
        assertThrows(IllegalArgumentException.class, () ->customerService.deleteById(customerA.getId()));
    }
}