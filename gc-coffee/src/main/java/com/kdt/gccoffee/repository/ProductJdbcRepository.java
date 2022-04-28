package com.kdt.gccoffee.repository;

import static com.kdt.gccoffee.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.gccoffee.repository.JdbcUtils.toUUID;

import com.kdt.gccoffee.model.Category;
import com.kdt.gccoffee.model.Product;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

@RequiredArgsConstructor
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Product insert(Product product) {
        int update = jdbcTemplate.update("INSERT INTO products(id, name, category, price, created_at, updated_at) " +
                "VALUES(UNHEX(REPLACE(:productId, '-', '')), :name, :category, :price, :createdAt, :updatedAt)", toParamMap(product));
        if (update != 1) {
            throw new RuntimeException("데이터 삽입에 실패했습니다.");
        }

        return product;
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = UNHEX(REPLACE(:id, '-', ''))",
                        Collections.singletonMap("id", toUUID(id.toString().getBytes())), productRowMapper
                )
        );
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM products WHERE name = UNHEX(REPLACE(:name, '-', ''))",
                        Collections.singletonMap("name", name), productRowMapper
                )
        );
    }

    @Override
    public Optional<Product> findByCategory(Category category) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject("SELECT * FROM products WHERE category = :category",
                        Collections.singletonMap("category", category), productRowMapper
                )
        );
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    private static final RowMapper<Product> productRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes("id"));
        String name = resultSet.getString("name");
        Category category = Category.valueOf(resultSet.getString("category"));
        long price = resultSet.getLong("price");
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        return new Product(id, name, category, price, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Product product) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("id", product.getId());
        paramMap.put("name", product.getName());
        paramMap.put("price", product.getPrice());
        paramMap.put("category", product.getCategory());
        paramMap.put("createdAt", product.getCreatedAt());
        paramMap.put("updatedAt", product.getUpdatedAt());

        return paramMap;
    }
}
