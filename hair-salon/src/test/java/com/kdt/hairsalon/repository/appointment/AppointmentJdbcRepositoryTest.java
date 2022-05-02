package com.kdt.hairsalon.repository.appointment;

import com.kdt.hairsalon.model.*;
import com.kdt.hairsalon.repository.customer.CustomerRepository;
import com.kdt.hairsalon.repository.designer.DesignerRepository;
import com.kdt.hairsalon.repository.menu.MenuRepository;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class AppointmentJdbcRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private DesignerRepository designerRepository;

    private static EmbeddedMysql embeddedMySql;

    private final Designer designerA = new Designer(UUID.randomUUID(), "designerA", Position.DESIGNER, LocalDateTime.now());
    private final Menu menu = new Menu(UUID.randomUUID(), "menuA", 1000, LocalDateTime.now(), LocalDateTime.now());
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

    private final Appointment appointmentA = new Appointment(UUID.randomUUID(), menu.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());
    private final Appointment appointmentB = new Appointment(UUID.randomUUID(), menu.getId(), customerB.getId(), designerA.getId(), LocalDateTime.now());

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

        menuRepository.insert(menu);
        designerRepository.insert(designerA);
        customerRepository.insert(customerA);
        customerRepository.insert(customerB);

    }

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @Order(1)
    @DisplayName("예약 추가 테스트")
    void insertTest() {
        //when
        appointmentRepository.insert(appointmentA);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentA.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(appointmentA));
    }

    @Test
    @Order(2)
    @DisplayName("예약 단일 조회 테스트")
    void findByAppointmentIdTest() {
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentA.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(appointmentA));
    }

    @Test
    @Order(3)
    @DisplayName("Customer ID로 예약정보 조회 테스트")
    void findByCustomerIdTest() {
        //when
        Optional<Appointment> foundAppointment = appointmentRepository.findByCustomerId(customerA.getId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(appointmentA));
    }

    @Test
    @Order(4)
    @DisplayName("디자이너 정보로 예약 정보 전체 조회 테스트")
    void findByDesignerIdTest() {
        //given
        appointmentRepository.insert(appointmentB);

        //when
        List<Appointment> foundAppointments = appointmentRepository.findByDesignerId(designerA.getId());

        //then
        assertThat(foundAppointments.size(), is(2));
        assertThat(foundAppointments, containsInAnyOrder(samePropertyValuesAs(appointmentA), samePropertyValuesAs(appointmentB)));
    }

    @Test
    @Order(5)
    @DisplayName("예약정보 전체 조회 테스트")
    void findAllTest() {
        //when
        List<Appointment> foundAppointments = appointmentRepository.findAll();

        //then
        assertThat(foundAppointments.size(), is(2));
        assertThat(foundAppointments, containsInAnyOrder(samePropertyValuesAs(appointmentA), samePropertyValuesAs(appointmentB)));
    }

    @Test
    @Order(6)
    @DisplayName("예약 정보 삭제 테스트")
    void deleteByAppointmentId() {
        //when
        appointmentRepository.deleteByAppointmentId(appointmentA.getAppointmentId());
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentA.getAppointmentId());

        //then
        assertThat(foundAppointment.isEmpty(), is(true));
    }

    @Test
    @Order(7)
    @DisplayName("예약 정보 수정 테스트")
    void updateByAppointmentId() {
        //given
        LocalDateTime updatedAppointedAt = LocalDateTime.now();
        Appointment updatedAppointment = new Appointment(appointmentB.getAppointmentId(), appointmentB.getMenuId(), appointmentB.getCustomerId(), appointmentB.getDesignerId(), updatedAppointedAt);

        //when
        appointmentRepository.updateByAppointmentId(updatedAppointment);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentB.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(updatedAppointment));
    }
}