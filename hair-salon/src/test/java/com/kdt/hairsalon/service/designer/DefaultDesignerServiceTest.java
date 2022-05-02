package com.kdt.hairsalon.service.designer;

import com.kdt.hairsalon.model.Position;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class DefaultDesignerServiceTest {

    @Autowired
    private DesignerService designerService;

    private static EmbeddedMysql embeddedMySql;

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
    @DisplayName("designer 추가 테스트")
    void createTest() {
        //when
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);
        DesignerDto foundDesigner = designerService.findById(designerA.getId());

        //then
        assertThat(designerA, samePropertyValuesAs(foundDesigner));
    }

    @Test
    @DisplayName("디자이너 정보 ID로 조회 테스트")
    void findByIdTest() {
        //given
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);

        //when
        DesignerDto foundDesigner = designerService.findById(designerA.getId());

        //then
        assertThat(designerA, samePropertyValuesAs(foundDesigner));
    }

    @Test
    @DisplayName("디자이너 정보 전체 조회 테스트")
    void findAllTest() {
        //given
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);
        DesignerDto designerB = designerService.create("designerB", Position.DESIGNER);

        //when
        List<DesignerDto> foundDesigners = designerService.findAll();

        //then
        assertThat(foundDesigners.size(), is(2));
        assertThat(foundDesigners, containsInAnyOrder(samePropertyValuesAs(designerA), samePropertyValuesAs(designerB)));
    }

    @Test
    @DisplayName("디자이너 정보 삭제 테스트")
    void deleteByIdTest() {
        //given
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);

        //when
        designerService.deleteById(designerA.getId());

        //then
        assertThrows(IllegalArgumentException.class, () -> designerService.findById(designerA.getId()));
    }

    @Test
    @DisplayName("디자이너 이름 변경 테스트")
    void updateDesignerNameTest() {
        //given
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);

        //when
        designerService.update(designerA.getId(), "designerB", Position.INTERN);
        DesignerDto foundDesigner = designerService.findById(designerA.getId());

        //then
        assertThat(foundDesigner.getId(), is(designerA.getId()));
        assertThat(foundDesigner.getName(), is("designerB"));
        assertThat(foundDesigner.getPosition(), is(Position.INTERN));
    }

    @Test
    @DisplayName("디자이너 직책 변경 테스트")
    void updateDesignerPositionTest() {
        //given
        DesignerDto designerA = designerService.create("designerA", Position.INTERN);

        //when
        designerService.update(designerA.getId(), "designerA", Position.DESIGNER);
        DesignerDto foundDesigner = designerService.findById(designerA.getId());

        //then
        assertThat(foundDesigner.getId(), is(designerA.getId()));
        assertThat(foundDesigner.getName(), is(designerA.getName()));
        assertThat(foundDesigner.getPosition(), is(Position.DESIGNER));
    }
}