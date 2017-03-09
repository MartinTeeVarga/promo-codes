package com.czequered.promocodes.repository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.czequered.promocodes.model.Code;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class CodeRepositoryTest {

    @Autowired
    CodeRepository codeRepository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(Code.class);

        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("access", "secret")))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8088", "ap-southeast-2"))
            .build();

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        codeRepository = new CodeRepository(mapper);
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
            codeRepository.save(code);

            Code otherCode = new Code();
            otherCode.setGameId("no-test");
            otherCode.setCodeId("PRV" + i);
            codeRepository.save(otherCode);
        }
        List<Code> all = codeRepository.findByGameId("auticko");
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
        codeRepository.save(code);

        Code retrieved = codeRepository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(retrieved).isEqualTo(code);
    }

    @Test
    public void findByGame() {
        Code game1Code = new Code();
        game1Code.setGameId("game1");
        game1Code.setCodeId("PUB1");
        codeRepository.save(game1Code);

        Code game2Code = new Code();
        game2Code.setGameId("game2");
        game2Code.setCodeId("PUB1");
        codeRepository.save(game2Code);

        List<Code> codes1 = codeRepository.findByGameId("game1");
        assertThat(codes1).containsExactly(game1Code);

        List<Code> codes2 = codeRepository.findByGameId("game2");
        assertThat(codes2).containsExactly(game2Code);
    }

    @Test
    public void delete() {
        Code code = new Code();
        code.setGameId("auticko");
        code.setCodeId("PUB1");
        codeRepository.save(code);
        Code foundBefore = codeRepository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(foundBefore).isEqualTo(code);
        codeRepository.delete(new Code("auticko", "PUB1"));
        Code foundAfter = codeRepository.findByGameIdAndCodeId("auticko", "PUB1");
        assertThat(foundAfter).isNull();
    }
}