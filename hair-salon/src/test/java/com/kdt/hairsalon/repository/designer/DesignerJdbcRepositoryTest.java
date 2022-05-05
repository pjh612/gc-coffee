package com.kdt.hairsalon.repository.designer;

import com.kdt.hairsalon.model.Designer;
import com.kdt.hairsalon.model.Position;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class DesignerJdbcRepositoryTest {

    @Autowired
    private DesignerRepository designerRepository;

    private static EmbeddedMysql embeddedMySql;

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
    void insertTest() {
        //given
        Designer designerA = new Designer(UUID.randomUUID(), "designerA", Position.DESIGNER, LocalDateTime.now());

        //when
        Designer insertedDesigner = designerRepository.insert(designerA);

        //then
        assertThat(insertedDesigner, samePropertyValuesAs(designerA));
    }


    @Test
    @DisplayName("디자이너 삭제 테스트")
    void deleteByIdTest() {
        //given
        Designer designerB = new Designer(UUID.randomUUID(), "designerB" ,Position.HEAD_DESIGNER,LocalDateTime.now());
        designerRepository.insert(designerB);

        //when
        designerRepository.deleteById(designerB.getId());
        Optional<Designer> foundDesigner = designerRepository.findById(designerB.getId());

        //then
        assertThat(foundDesigner.isEmpty(), is(true));
    }

    @Test
    @DisplayName("designerB 포지션 = HEAD_DESIGNER -> DESIGNER")
    void updatePositionTest() {
        //given
        Designer designerC = new Designer(UUID.randomUUID(), "designerC" ,Position.HEAD_DESIGNER,LocalDateTime.now());
        designerRepository.insert(designerC);

        //when
        designerRepository.update(designerC.getId(),"designerC", Position.DESIGNER);
        Optional<Designer> foundDesigner = designerRepository.findById(designerC.getId());

        //then
        assertThat(foundDesigner.isPresent() , is(true));
        assertThat(foundDesigner.get().getPosition(), is(Position.DESIGNER));
    }

    @Test
    @DisplayName("designerD 이름 = designerB -> designerE")
    void updateNameTest() {
        //given
        Designer designerD = new Designer(UUID.randomUUID(), "designerD" ,Position.HEAD_DESIGNER,LocalDateTime.now());
        designerRepository.insert(designerD);

        //when
        designerRepository.update(designerD.getId(),"designerE", Position.DESIGNER);
        Optional<Designer> foundDesigner = designerRepository.findById(designerD.getId());

        //then
        assertThat(foundDesigner.isPresent() , is(true));
        assertThat(foundDesigner.get().getName(), is("designerE"));
    }
}