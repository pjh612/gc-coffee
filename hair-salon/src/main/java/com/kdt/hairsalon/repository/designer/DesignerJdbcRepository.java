package com.kdt.hairsalon.repository.designer;

import com.kdt.hairsalon.model.Designer;
import com.kdt.hairsalon.model.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

@RequiredArgsConstructor
@Repository
public class DesignerJdbcRepository implements DesignerRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Designer insert(Designer designer) {
        int update = jdbcTemplate.update("INSERT INTO designers(id, name, position, joined_at) VALUES(UNHEX(REPLACE(:id, '-', '')), :name, :position, :joinedAt)"
                , toParamMap(designer));

        if (update != 1) {
            throw new RuntimeException("디자이너 정보 삽입에 실패했습니다.");
        }

        return designer;
    }

    @Override
    public Optional<Designer> findById(UUID id) {
        try {
            return Optional.of(
                    jdbcTemplate.queryForObject("SELECT * FROM designers WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                            Collections.singletonMap("id", id.toString().getBytes()),
                            designerRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Designer> findAll() {
        return jdbcTemplate.query("SELECT * FROM designers", designerRowMapper);
    }

    @Override
    public void deleteById(UUID id) {
        int update = jdbcTemplate.update("DELETE FROM designers WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Collections.singletonMap("id", id.toString().getBytes()));

        if (update != 1)
            throw new RuntimeException("디자이너 정보가 삭제되지 않았습니다.");
    }

    @Override
    public UUID update(UUID id, String name, Position position) {
        int update = jdbcTemplate.update("UPDATE designers SET name = :name, position = :position WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                Map.of(
                        "id", id.toString().getBytes(),
                        "name", name,
                        "position", position.toString()
                ));

        if (update != 1)
            throw new RuntimeException("디자이너 정보를 수정할 수 없습니다.");

        return id;
    }

    private static final RowMapper<Designer> designerRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        Position position = Position.valueOf(resultSet.getString("position"));
        LocalDateTime joinedAt = toLocalDateTime(resultSet.getTimestamp("joined_at"));

        return new Designer(id, name, position, joinedAt);
    };

    private Map<String, Object> toParamMap(Designer designer) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("id", designer.getId().toString().getBytes());
        paramMap.put("name", designer.getName());
        paramMap.put("position", designer.getPosition().toString());
        paramMap.put("joinedAt", designer.getJoinedAt());

        return paramMap;
    }
}
