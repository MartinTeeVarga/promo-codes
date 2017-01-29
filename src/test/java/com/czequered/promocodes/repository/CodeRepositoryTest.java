package com.czequered.promocodes.repository;

import com.czequered.promocodes.LocalDynamoDBCreationRule;
import com.czequered.promocodes.model.Code;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CodeRepositoryTest {

    @Autowired
    CodeRepository repository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Test
    public void saveAndFindByGameAndCodeTest() {
        Code code = new Code();
        code.setGame("test");
        code.setCode("PUB1");
        code.setFrom("2012-01-27T03:47:26+00:00");
        code.setTo("2037-01-27T03:47:26+00:00");
        code.setPub(true);
        code.setPayload("Hello World");
        repository.save(code);

        Code retrieved = repository.findByGameAndCode("test", "PUB1");
        assertThat(retrieved).isEqualTo(code);
    }
}