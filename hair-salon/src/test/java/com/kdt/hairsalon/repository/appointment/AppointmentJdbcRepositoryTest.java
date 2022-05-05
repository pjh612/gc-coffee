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
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
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
    @DisplayName("예약 추가 테스트")
    void insertTest() {
        //given
        Appointment appointmentA = new Appointment(UUID.randomUUID(), designerA, customerA, menu, AppointmentStatus.APPOINTED, LocalDateTime.now().plusMinutes(90));

        //when
        appointmentRepository.insert(appointmentA);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentA.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(appointmentA));
    }

    @Test
    @DisplayName("예약 정보 삭제 테스트")
    void deleteByAppointmentId() {
        //given
        Appointment appointmentA = new Appointment(UUID.randomUUID(), designerA, customerA, menu, AppointmentStatus.APPOINTED, LocalDateTime.now().plusMinutes(60));
        appointmentRepository.insert(appointmentA);

        //when
        appointmentRepository.deleteByAppointmentId(appointmentA.getAppointmentId());
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentA.getAppointmentId());

        //then
        assertThat(foundAppointment.isEmpty(), is(true));
    }

    @Test
    @DisplayName("예약 시간 수정 테스트")
    void updateByAppointmentId() {
        //given
        Appointment appointmentB = new Appointment(UUID.randomUUID(), designerA, customerB, menu, AppointmentStatus.APPOINTED, LocalDateTime.now().plusMinutes(30));
        appointmentRepository.insert(appointmentB);

        LocalDateTime updatedAppointedAt = LocalDateTime.now();
        Appointment updatedAppointment = new Appointment(appointmentB.getAppointmentId(), appointmentB.getDesigner(), appointmentB.getCustomer(), appointmentB.getMenu(), AppointmentStatus.APPOINTED, updatedAppointedAt);

        //when
        appointmentRepository.updateByAppointmentId(updatedAppointment);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentB.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get(), samePropertyValuesAs(updatedAppointment));
    }

    @Test
    @DisplayName("예약상태에서 완료 상태로 변경 테스트")
    void updateStatusToDone() {
        //given
        Appointment appointmentB = new Appointment(UUID.randomUUID(), designerA, customerB, menu, AppointmentStatus.APPOINTED, LocalDateTime.now().plusMinutes(120));
        appointmentRepository.insert(appointmentB);

        Appointment updatedAppointment = new Appointment(appointmentB.getAppointmentId(), appointmentB.getDesigner(), appointmentB.getCustomer(), appointmentB.getMenu(), AppointmentStatus.DONE, appointmentB.getAppointedAt());

        //when
        appointmentRepository.updateByAppointmentId(updatedAppointment);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentB.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get().getStatus(), is(AppointmentStatus.DONE));
        assertThat(foundAppointment.get(), samePropertyValuesAs(updatedAppointment));
    }

    @Test
    @DisplayName("예약 상태로 변경 테스트")
    void updateStatusToAppointed() {
        //given
        Appointment appointmentB = new Appointment(UUID.randomUUID(), designerA, customerB, menu, AppointmentStatus.APPOINTED, LocalDateTime.now().plusMinutes(150));
        appointmentRepository.insert(appointmentB);

        Appointment updatedAppointment = new Appointment(appointmentB.getAppointmentId(), appointmentB.getDesigner(), appointmentB.getCustomer(), appointmentB.getMenu(), AppointmentStatus.APPOINTED, appointmentB.getAppointedAt());

        //when
        appointmentRepository.updateByAppointmentId(updatedAppointment);
        Optional<Appointment> foundAppointment = appointmentRepository.findByAppointmentId(appointmentB.getAppointmentId());

        //then
        assertThat(foundAppointment.isPresent(), is(true));
        assertThat(foundAppointment.get().getStatus(), is(AppointmentStatus.APPOINTED));
        assertThat(foundAppointment.get(), samePropertyValuesAs(updatedAppointment));
    }
}