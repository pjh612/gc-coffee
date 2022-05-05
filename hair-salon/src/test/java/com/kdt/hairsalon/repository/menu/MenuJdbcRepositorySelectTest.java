package com.kdt.hairsalon.repository.menu;

import com.kdt.hairsalon.model.Menu;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class MenuJdbcRepositorySelectTest {

    @Autowired
    private MenuRepository menuRepository;

    private static EmbeddedMysql embeddedMySql;


    private final Menu menuA = new Menu(UUID.randomUUID(), "menuA", 1000, LocalDateTime.now(), LocalDateTime.now());
    private final Menu menuB = new Menu(UUID.randomUUID(), "menuB", 1000, LocalDateTime.now(), LocalDateTime.now());
    private final Menu menuC = new Menu(UUID.randomUUID(), "menuC", 1000, LocalDateTime.now(), LocalDateTime.now());

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
        menuRepository.insert(menuA);
        menuRepository.insert(menuB);
        menuRepository.insert(menuC);
    }

    @AfterAll
    static void cleanUp() {
        embeddedMySql.stop();
    }


    @Test
    @DisplayName("메뉴를 ID로 조회할 수 있다.")
    void findByIdTest() {


        //when
        Optional<Menu> foundMenu = menuRepository.findById(menuA.getId());

        //then
        assertThat(foundMenu.isPresent(), is(true));
        assertThat(foundMenu.get(), samePropertyValuesAs(menuA));
    }

    @Test
    void findAllTest() {
        //when
        List<Menu> menus = menuRepository.findAll();

        //then
        assertThat(menus.size(), is(3));
        assertThat(menus, containsInAnyOrder(samePropertyValuesAs(menuA), samePropertyValuesAs(menuB), samePropertyValuesAs(menuC)));
    }
}