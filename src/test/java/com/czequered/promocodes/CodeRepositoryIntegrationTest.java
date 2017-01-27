package com.czequered.promocodes;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.repositories.CodeRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CodeRepositoryIntegrationTest {

    @Autowired
    CodeRepository repository;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Test
    public void saveAndFindByGameAncCodeTest() {
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