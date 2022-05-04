package com.kdt.hairsalon.service.menu;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class DefaultMenuServiceTest {

    @Autowired
    private MenuService menuService;

    private  EmbeddedMysql embeddedMySql;

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

    }

    @AfterAll
     void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("정상 시술 메뉴 추가 테스트")
    void insertTest() {
        //when
        MenuDto menuA = menuService.insert("menuA", 12000);
        MenuDto foundMenu = menuService.findById(menuA.getId());

        //then
        assertThat(foundMenu, samePropertyValuesAs(menuA));
    }

    @Test
    @DisplayName("시술 메뉴 ID로 조회 테스트")
    void findByIdTest() {
        //given
        MenuDto menuA = menuService.insert("menuA", 12000);


        //when
        MenuDto foundMenu = menuService.findById(menuA.getId());
        //then
        assertThat(foundMenu, samePropertyValuesAs(menuA));
    }

    @Test
    @DisplayName("시술 메뉴 전체 조회 테스트")
    void findAllTest() {
        //given
        MenuDto menuA = menuService.insert("menuA", 12000);
        MenuDto menuB = menuService.insert("menuB", 12000);

        //when
        List<MenuDto> foundMenus = menuService.findAll();

        //then
        assertThat(foundMenus.size(), is(2));
        assertThat(foundMenus, containsInAnyOrder(samePropertyValuesAs(menuA),samePropertyValuesAs(menuB)));
    }

    @Test
    @DisplayName("Menu 삭제 테스트")
    void deleteById() {
        //given
        MenuDto menuA = menuService.insert("menuA", 12000);

        //when
        menuService.deleteById(menuA.getId());

        //then
        assertThrows(IllegalArgumentException.class, () ->menuService.findById(menuA.getId()));
    }
}