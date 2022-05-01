package com.kdt.gccoffee.repository.customer;

import com.kdt.gccoffee.model.Customer;
import com.kdt.gccoffee.model.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.kdt.gccoffee.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.gccoffee.repository.JdbcUtils.toUUID;

@RequiredArgsConstructor
@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Customer insert(Customer customer) {
        int update = jdbcTemplate.update("INSERT INTO customers(id, name, email, gender, birth, created_at, updated_at) " +
                "VALUES(UNHEX(REPLACE(:id, '-', '')), :name, :email, :gender, :birth, :createdAt, :updatedAt)", toParamMap(customer));
        if (update != 1) {
            throw new RuntimeException("데이터 삽입에 실패했습니다.");
        }

        return customer;
    }

    @Override
    public Optional<Customer> findById(UUID id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                        Collections.singletonMap("id", toUUID(id.toString().getBytes())), productRowMapper
                )
        );
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM customers WHERE name = :name",
                        Collections.singletonMap("name", name), productRowMapper
        );
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                        Collections.singletonMap("email", email), productRowMapper
                )
        );
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", productRowMapper);
    }

    private static final RowMapper<Customer> productRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        Gender gender = Gender.valueOf(resultSet.getString("gender"));
        LocalDate birth = resultSet.getDate("birth").toLocalDate();
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Customer(id, name, email, gender, birth, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("id", customer.getId());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("gender", customer.getGender().toString());
        paramMap.put("birth", customer.getBirth());
        paramMap.put("createdAt", customer.getCreatedAt());
        paramMap.put("updatedAt", customer.getUpdatedAt());

        return paramMap;
    }
}
