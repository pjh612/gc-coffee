package com.kdt.hairsalon.repository.designer;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.kdt.hairsalon.model.Designer;
import com.kdt.hairsalon.model.Position;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class DesignerJdbcRepositoryTest {

    @Autowired
    private DesignerRepository designerRepository;

    private static EmbeddedMysql embeddedMySql;

    private final Designer designerA = new Designer(UUID.randomUUID(), "designerA", Position.DESIGNER, LocalDateTime.now());
   private final Designer designerB = new Designer(UUID.randomUUID(), "designerB" ,Position.HEAD_DESIGNER,LocalDateTime.now());

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
    static void cleanUp() {
        embeddedMySql.stop();
    }

    @Test
    @DisplayName("디자이너 추가 테스트")
    @Order(1)
    void insertTest() {
        Designer insertedDesigner = designerRepository.insert(designerA);

        assertThat(insertedDesigner, samePropertyValuesAs(designerA));
    }

    @Test
    @DisplayName("디자이너 단일 조회 테스트")
    @Order(2)
    void findByIdTest() {
        Optional<Designer> foundDesigner = designerRepository.findById(designerA.getId());

        assertThat(foundDesigner.isPresent(), is(true));
        assertThat(foundDesigner.get(), samePropertyValuesAs(designerA));
    }

    @Test
    @DisplayName("디자이너 전체 조회 테스트")
    @Order(3)
    void findAllTest() {
        //given
        Designer designerC = new Designer(UUID.randomUUID(), "designerC" ,Position.INTERN,LocalDateTime.now());
        designerRepository.insert(designerB);
        designerRepository.insert(designerC);

        //when
        List<Designer> designers = designerRepository.findAll();
        //then
        assertThat(designers.size(), is(3));
        assertThat(designers, containsInAnyOrder(samePropertyValuesAs(designerA), samePropertyValuesAs(designerB), samePropertyValuesAs(designerC)));
    }

    @Test
    @DisplayName("디자이너 삭제 테스트")
    @Order(4)
    void deleteByIdTest() {
        //when
        designerRepository.deleteById(designerA.getId());
        Optional<Designer> foundDesigner = designerRepository.findById(designerA.getId());

        //then
        assertThat(foundDesigner.isEmpty(), is(true));
    }

    @Test
    @Order(5)
    @DisplayName("designerB 포지션 = HEAD_DESIGNER -> DESIGNER")
    void updatePositionTest() {
        //when
        designerRepository.update(designerB.getId(),"designerB", Position.DESIGNER);
        Optional<Designer> foundDesigner = designerRepository.findById(designerB.getId());

        //then
        assertThat(foundDesigner.isPresent() , is(true));
        assertThat(foundDesigner.get().getPosition(), is(Position.DESIGNER));
    }

    @Test
    @Order(6)
    @DisplayName("designerB 이름 = designerB -> designerD")
    void updateNameTest() {
        //when
        designerRepository.update(designerB.getId(),"designerD", Position.DESIGNER);
        Optional<Designer> foundDesigner = designerRepository.findById(designerB.getId());

        //then
        assertThat(foundDesigner.isPresent() , is(true));
        assertThat(foundDesigner.get().getName(), is("designerD"));
    }
}