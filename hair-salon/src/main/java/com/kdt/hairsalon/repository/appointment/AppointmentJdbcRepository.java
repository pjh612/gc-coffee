package com.kdt.hairsalon.repository.appointment;

import com.kdt.hairsalon.controller.api.exception.NotValidAppointmentInsertException;
import com.kdt.hairsalon.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

@Repository
@RequiredArgsConstructor
public class AppointmentJdbcRepository implements AppointmentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Appointment insert(Appointment appointment) {
        try {
            int update = jdbcTemplate.update("INSERT INTO appointments(appointment_id, menu_id, customer_id, designer_id, status, appointed_at) " +
                            "VALUES(UNHEX(REPLACE(:appointmentId, '-', ''))," +
                            " UNHEX(REPLACE(:menuId, '-', ''))," +
                            " UNHEX(REPLACE(:customerId, '-', ''))," +
                            " UNHEX(REPLACE(:designerId, '-', ''))," +
                            " :status," +
                            " :appointedAt)",
                    toParamMap(appointment));

            if (update != 1) {
                throw new RuntimeException("데이터 저장에 실패 했습니다.");
            }

            return appointment;
        } catch (DuplicateKeyException e) {
            throw new NotValidAppointmentInsertException("같은 시간에 이미 예약한 회원 입니다.", e);
        }
    }

    @Override
    public void deleteByAppointmentId(UUID appointmentId) {
        int update = jdbcTemplate.update("DELETE FROM appointments WHERE appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))"
                , Collections.singletonMap("appointmentId", appointmentId.toString().getBytes()));

        if (update != 1) {
            throw new RuntimeException("데이터 삭제에 실패했습니다.");
        }
    }

    @Override
    public Optional<Appointment> findByAppointmentId(UUID appointmentId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM appointments AS ap " +
                            "join customers AS c on ap.customer_id = c.id " +
                            "join designers AS d on ap.designer_id = d.id " +
                            "join menus AS m on ap.menu_id = m.id " +
                            "WHERE ap.appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))",
                    Collections.singletonMap("appointmentId", appointmentId.toString().getBytes()), appointmentRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Appointment> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM appointments AS ap " +
                                    "join customers AS c on ap.customer_id = c.id " +
                                    "join designers AS d on ap.designer_id = d.id " +
                                    "join menus AS m on ap.menu_id = m.id " +
                                    "WHERE c.id = UNHEX(REPLACE(:customerId, '-', ''))",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()), appointmentRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Appointment> findByStatus(AppointmentStatus status) {
        return jdbcTemplate.query("SELECT * FROM appointments AS ap " +
                        "join customers AS c on ap.customer_id = c.id " +
                        "join designers AS d on ap.designer_id = d.id " +
                        "join menus AS m on ap.menu_id = m.id " +
                        "WHERE ap.status = :status",
                Collections.singletonMap("status", status.toString()), appointmentRowMapper);
    }

    @Override
    public List<Appointment> findByDesignerId(UUID designerId) {
        return jdbcTemplate.query("SELECT * FROM appointments AS ap " +
                        "join customers AS c on ap.customer_id = c.id " +
                        "join designers AS d on ap.designer_id = d.id " +
                        "join menus AS m on ap.menu_id = m.id " +
                        "WHERE d.id = UNHEX(REPLACE(:designerId, '-', ''))",
                Collections.singletonMap("designerId", designerId.toString().getBytes()), appointmentRowMapper);
    }

    @Override
    public List<Appointment> findAll() {
        return jdbcTemplate.query("SELECT * FROM appointments AS ap " +
                "join customers AS c on ap.customer_id = c.id " +
                "join designers AS d on ap.designer_id = d.id " +
                "join menus AS m on ap.menu_id = m.id", appointmentRowMapper);
    }

    @Override
    public Appointment updateByAppointmentId(Appointment appointment) {
        int update = jdbcTemplate.update("UPDATE appointments SET menu_id = UNHEX(REPLACE(:menuId, '-', '')), status = :status, appointed_at = :appointedAt WHERE appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))",
                toParamMap(appointment));

        if (update != 1)
            throw new RuntimeException("예약 정보 수정에 실패했습니다.");

        return appointment;
    }

    private Map<String, Object> toParamMap(Appointment appointment) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("appointmentId", appointment.getAppointmentId().toString().getBytes());
        paramMap.put("customerId", appointment.getCustomer().getId().toString().getBytes());
        paramMap.put("menuId", appointment.getMenu().getId().toString().getBytes());
        paramMap.put("designerId", appointment.getDesigner().getId().toString().getBytes());
        paramMap.put("status", appointment.getStatus().toString());
        paramMap.put("appointedAt", appointment.getAppointedAt());

        return paramMap;
    }

    private static final RowMapper<Appointment> appointmentRowMapper = (resultSet, i) -> {
        UUID appointmentId = toUUID(resultSet.getBytes("ap.appointment_id"));
        AppointmentStatus status = AppointmentStatus.valueOf(resultSet.getString("ap.status"));
        LocalDateTime appointedAt = toLocalDateTime(resultSet.getTimestamp("ap.appointed_at"));

        //menu
        UUID menuId = toUUID(resultSet.getBytes("m.id"));
        String menuName = resultSet.getString("m.name");
        int menuPrice = resultSet.getInt("m.price");
        LocalDateTime menuCreatedAt = toLocalDateTime(resultSet.getTimestamp("m.created_at"));
        LocalDateTime menuUpdatedAt = toLocalDateTime(resultSet.getTimestamp("m.updated_at"));

        //customer
        UUID customerId = toUUID(resultSet.getBytes("c.id"));
        String customerName = resultSet.getString("c.name");
        String customerEmail = resultSet.getString("c.email");
        Gender customerGender = Gender.valueOf(resultSet.getString("c.gender"));
        LocalDate customerBirth = resultSet.getDate("c.birth").toLocalDate();
        String customerComment = resultSet.getString("c.comment");
        LocalDateTime customerCreatedAt = toLocalDateTime(resultSet.getTimestamp("c.created_at"));
        LocalDateTime customerUpdatedAt = toLocalDateTime(resultSet.getTimestamp("c.updated_at"));

        //designer
        UUID designerId = toUUID(resultSet.getBytes("d.id"));
        String designerName = resultSet.getString("d.name");
        Position designerPosition = Position.valueOf(resultSet.getString("d.position"));
        LocalDateTime designerJoinedAt = toLocalDateTime(resultSet.getTimestamp("d.joined_at"));

        Menu menu = new Menu(menuId, menuName, menuPrice, menuCreatedAt, menuUpdatedAt);
        Customer customer = new Customer(customerId, customerName, customerEmail, customerGender, customerBirth, customerCreatedAt, customerUpdatedAt, customerComment);
        Designer designer = new Designer(designerId, designerName, designerPosition, designerJoinedAt);

        return new Appointment(appointmentId, designer, customer, menu, status, appointedAt);
    };
}
