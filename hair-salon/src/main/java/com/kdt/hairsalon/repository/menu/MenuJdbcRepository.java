package com.kdt.hairsalon.repository.menu;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

import com.kdt.hairsalon.model.Menu;

import java.time.LocalDateTime;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MenuJdbcRepository implements MenuRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Menu insert(Menu menu) {
        int update = jdbcTemplate.update("INSERT INTO menus(id, name, price, created_at, updated_at) " +
                "VALUES(UNHEX(REPLACE(:id, '-', '')), :name, :price, :createdAt, :updatedAt)", toParamMap(menu));

        if (update != 1) {
            throw new RuntimeException("데이터 삽입에 실패했습니다.");
        }

        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM menus WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                    Collections.singletonMap("id", id.toString().getBytes()),
                    menuRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void deleteById(UUID id) {
        int update = jdbcTemplate.update("DELETE FROM menus WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString().getBytes()));

        if (update != 1)
            throw new RuntimeException("메뉴 정보가 삭제되지 않았습니다.");
    }

    @Override
    public List<Menu> findAll() {
        return jdbcTemplate.query("SELECT * FROM menus", menuRowMapper);
    }

    private static final RowMapper<Menu> menuRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        int price = resultSet.getInt("price");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Menu(id, name, price, createdAt,updatedAt);
    };

    private Map<String, Object> toParamMap(Menu menu) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("id", menu.getId().toString().getBytes());
        paramMap.put("name", menu.getName());
        paramMap.put("price", menu.getPrice());
        paramMap.put("updatedAt", menu.getUpdatedAt());
        paramMap.put("createdAt", menu.getCreatedAt());

        return paramMap;
    }
}
