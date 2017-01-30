package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.Code;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    @Test(expected = IllegalArgumentException.class)
    public void findAllTest() {
        repository.findAll();
    }

    @Test
    public void findAllPageableTest() {
        for (int i = 0; i < 4; i++) {
            Code code = new Code();
            code.setGameId("test");
            code.setCodeId("PUB" + i);
            repository.save(code);
        }
        Page<Code> all = repository.findAll(new PageRequest(0, 3));
        assertThat(all.getTotalElements()).isEqualTo(4);
        assertThat(all.getTotalPages()).isEqualTo(2);
        assertThat(all.getContent()).hasSize(3);
    }

    @Test
    public void findAllPageNotFoundTest() {
        for (int i = 0; i < 3; i++) {
            Code code = new Code();
            code.setGameId("test");
            code.setCodeId("PUB" + i);
            repository.save(code);
        }
        Page<Code> all = repository.findAll(new PageRequest(1, 3));
        all.forEach(c -> System.out.println("c.getCodeId() = " + c.getCodeId()));
        assertThat(all.getTotalElements()).isEqualTo(3);
        assertThat(all.getTotalPages()).isEqualTo(1);
        assertThat(all.getContent()).hasSize(0);
    }

    @Test
    public void findByGameAndCodeTest() {
        Code code = new Code();
        code.setGameId("test");
        code.setCodeId("PUB1");
        code.setFrom("2012-01-27T03:47:26+00:00");
        code.setTo("2037-01-27T03:47:26+00:00");
        code.setPub(true);
        code.setPayload("Hello World");
        repository.save(code);

        Code retrieved = repository.findByGameIdAndCodeId("test", "PUB1");
        assertThat(retrieved).isEqualTo(code);
    }

    @Test
    public void findByGameTest() {
        Code game1Code = new Code();
        game1Code.setGameId("game1");
        game1Code.setCodeId("PUB1");
        repository.save(game1Code);

        Code game2Code = new Code();
        game2Code.setGameId("game2");
        game2Code.setCodeId("PUB1");
        repository.save(game2Code);

        Page<Code> retrieved1 = repository.findByGameId("game1", new PageRequest(0, 2));
        assertThat(retrieved1).containsExactly(game1Code);

        Page<Code> retrieved2 = repository.findByGameId("game2", new PageRequest(0, 2));
        assertThat(retrieved2).containsExactly(game2Code);
    }
}