package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.Code;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CodeRepositoryTest {

    @Autowired
    CodeRepository repository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(Code.class);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(Code.class);
    }

    @Test
    public void findAllByGameId() {
        for (int i = 0; i < 4; i++) {
            Code code = new Code();
            code.setGameId("auticko");
            code.setCodeId("PUB" + i);
            repository.save(code);

            Code otherCode = new Code();
            otherCode.setGameId("no-test");
            otherCode.setCodeId("PRV" + i);
            repository.save(otherCode);
        }
        List<Code> all = repository.findByGameId("auticko");
        assertThat(all).hasSize(4);
    }

    @Test
    public void findByGameAndCode() {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB1");
        code.setFrom("2012-01-27T03:47:26+00:00");
        code.setTo("2037-01-27T03:47:26+00:00");
        code.setPub(true);
        code.setPayload("Hello World");
        repository.save(code);

        Code retrieved = repository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(retrieved).isEqualTo(code);
    }

    @Test
    public void findByGame() {
        Code game1Code = new Code();
        game1Code.setGameId("game1");
        game1Code.setCodeId("PUB1");
        repository.save(game1Code);

        Code game2Code = new Code();
        game2Code.setGameId("game2");
        game2Code.setCodeId("PUB1");
        repository.save(game2Code);

        List<Code> codes1 = repository.findByGameId("game1");
        assertThat(codes1).containsExactly(game1Code);

        List<Code> codes2 = repository.findByGameId("game2");
        assertThat(codes2).containsExactly(game2Code);
    }

    @Test
    public void delete() {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB1");
        repository.save(code);
        Code foundBefore = repository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(foundBefore).isEqualTo(code);
        repository.delete(new Code("auticko", "PUB1"));
        Code foundAfter = repository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(foundAfter).isNull();
    }
}