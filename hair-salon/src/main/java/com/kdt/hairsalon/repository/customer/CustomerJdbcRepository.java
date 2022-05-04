package com.kdt.hairsalon.repository.customer;

import com.kdt.hairsalon.model.Customer;
import com.kdt.hairsalon.model.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

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
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                            Collections.singletonMap("id", id.toString().getBytes()), productRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query("SELECT * FROM customers WHERE name = :name",
                Collections.singletonMap("name", name), productRowMapper
        );
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM customers WHERE email = :email",
                            Collections.singletonMap("email", email), productRowMapper
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customers", productRowMapper);
    }

    @Override
    public void deleteById(UUID id) {
        int update = jdbcTemplate.update("DELETE FROM customers WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString().getBytes()));

        if (update != 1)
            throw new RuntimeException("Customer 정보가 삭제되지 않았습니다.");
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

        paramMap.put("id", customer.getId().toString().getBytes());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("gender", customer.getGender().toString());
        paramMap.put("birth", customer.getBirth());
        paramMap.put("createdAt", customer.getCreatedAt());
        paramMap.put("updatedAt", customer.getUpdatedAt());

        return paramMap;
    }
}
