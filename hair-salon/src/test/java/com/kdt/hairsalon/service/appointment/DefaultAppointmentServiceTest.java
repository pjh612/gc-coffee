package com.kdt.hairsalon.service.appointment;

import com.kdt.hairsalon.model.Gender;
import com.kdt.hairsalon.model.Position;
import com.kdt.hairsalon.service.customer.CustomerDto;
import com.kdt.hairsalon.service.customer.CustomerService;
import com.kdt.hairsalon.service.designer.DesignerDto;
import com.kdt.hairsalon.service.designer.DesignerService;
import com.kdt.hairsalon.service.menu.MenuDto;
import com.kdt.hairsalon.service.menu.MenuService;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class DefaultAppointmentServiceTest {
    private static EmbeddedMysql embeddedMySql;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private DesignerService designerService;

    @Autowired
    private AppointmentService appointmentService;

    MenuDto menuA;
    MenuDto menuB;
    DesignerDto designerA;
    CustomerDto customerA;
    CustomerDto customerB;

    @BeforeEach
    void dataInit() {
        menuA = menuService.insert("menuA", 12000);
        menuB = menuService.insert("menuB", 30000);
        designerA = designerService.create("designerA", Position.INTERN);
        customerA = customerService.create("customerA", "customera@gmail.com", Gender.MAN, LocalDate.now());
        customerB = customerService.create("customerB", "customerb@gmail.com", Gender.WOMAN, LocalDate.now());
    }

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

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("예약 정보 추가 테스트")
    void makeTest() {
        AppointmentDto appointment = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());

        AppointmentDto foundAppointment = appointmentService.findByAppointmentId(appointment.getAppointmentId());

        assertThat(appointment, samePropertyValuesAs(foundAppointment));
    }

    @Test
    @DisplayName("예약 정보 전체 조회 테스트")
    void findAllTest() {
        AppointmentDto appointmentA = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());
        AppointmentDto appointmentB = appointmentService.make(menuA.getId(), customerB.getId(), designerA.getId(), LocalDateTime.now());

        List<AppointmentDto> foundAppointments = appointmentService.findAll();

        assertThat(foundAppointments.size(), is(2));
        assertThat(foundAppointments, containsInAnyOrder(samePropertyValuesAs(appointmentA), samePropertyValuesAs(appointmentB)));
    }

    @Test
    @DisplayName("예약 ID로 예약정보 조회")
    void findByAppointmentIdTest() {
        //given
        AppointmentDto appointment = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());

        //when
        AppointmentDto foundAppointment = appointmentService.findByAppointmentId(appointment.getAppointmentId());

        //then
        assertThat(foundAppointment, samePropertyValuesAs(appointment));
    }

    @Test
    @DisplayName("CustomerId로 예약정보 조회")
    void findByCustomerIdTest() {
        //given
        AppointmentDto appointment = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());

        //when
        AppointmentDto foundAppointment = appointmentService.findByCustomerId(customerA.getId());

        //then
        assertThat(foundAppointment, samePropertyValuesAs(appointment));
    }

    @Test
    @DisplayName("Designer ID로 예약정보 조회")
    void findByDesignerIdTest() {
        //given
        AppointmentDto appointmentA = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());
        AppointmentDto appointmentB = appointmentService.make(menuA.getId(), customerB.getId(), designerA.getId(), LocalDateTime.now());

        //when
        List<AppointmentDto> foundAppointments = appointmentService.findByDesignerId(designerA.getId());

        //then
        assertThat(foundAppointments, containsInAnyOrder(samePropertyValuesAs(appointmentA), samePropertyValuesAs(appointmentB)));
    }

    @Test
    @DisplayName("AppointmentId로 예약정보 삭제")
    void deleteByAppointmentIdTest() {
        //given
        AppointmentDto appointment = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());

        //when
        appointmentService.deleteByAppointmentId(appointment.getAppointmentId());


        //then
        assertThrows(IllegalArgumentException.class, () -> appointmentService.findByAppointmentId(appointment.getAppointmentId()));
    }

    @Test
    @DisplayName("AppointmentID로 예약 정보 업데이트(예약시간 변경)")
    void updatedByAppointmentIdTest() {
        //given
        AppointmentDto toUpdateAppointment = appointmentService.make(menuA.getId(), customerA.getId(), designerA.getId(), LocalDateTime.now());

        //when
        appointmentService.updatedByAppointmentId(toUpdateAppointment.getAppointmentId(), toUpdateAppointment.getAppointedAt());
        AppointmentDto foundAppointment = appointmentService.findByAppointmentId(toUpdateAppointment.getAppointmentId());

        //then
        assertThat(foundAppointment.getMenuDto().getId(), is(menuA.getId()));
        assertThat(foundAppointment.getAppointmentId(), is(toUpdateAppointment.getAppointmentId()));
        assertThat(foundAppointment.getAppointedAt(), is(toUpdateAppointment.getAppointedAt()));
    }
}