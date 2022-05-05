package com.kdt.hairsalon.repository.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

@Repository
@RequiredArgsConstructor
public class AppointmentWithNamesJdbcRepository implements AppointmentWithNamesRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<AppointmentWithNames> findAll() {
        return jdbcTemplate.query("SELECT ap.appointment_id,c.id, c.name,d.id, d.name,m.id, m.name, ap.appointed_at FROM appointments AS ap " +
                "join customers AS c on ap.customer_id = c.id " +
                "join designers AS d on ap.designer_id = d.id " +
                "join menus AS m on ap.menu_id = m.id", appointmentJoinRowMapper);
    }

    private static final RowMapper<AppointmentWithNames> appointmentJoinRowMapper = (resultSet, i) -> {
        UUID appointmentId = toUUID(resultSet.getBytes("ap.appointment_id"));
        UUID menuId = toUUID(resultSet.getBytes("m.id"));
        UUID customerId = toUUID(resultSet.getBytes("c.id"));
        UUID designerId = toUUID(resultSet.getBytes("d.id"));
        String menuName = resultSet.getString("m.name");
        String customerName = resultSet.getString("c.name");
        String designerName = resultSet.getString("d.name");
        LocalDateTime appointedAt = toLocalDateTime(resultSet.getTimestamp("ap.appointed_at"));

        return new AppointmentWithNames(appointmentId, designerId, customerId, menuId, designerName, menuName, customerName, appointedAt);
    };
}
