package com.czequered.promocodes.repository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.czequered.promocodes.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(User.class);

        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("access", "secret")))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8088", "ap-southeast-2"))
            .build();

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        userRepository = new UserRepository(mapper);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(User.class);
    }


    @Test
    public void findByUserId() {
        User krtek = new User("Krtek");
        krtek.addAttribute("hello", "world");
        User sova = new User("Sova");

        userRepository.save(krtek);
        userRepository.save(sova);

        User krtekRetrieved = userRepository.findByUserId("Krtek");
        assertThat(krtekRetrieved).isEqualToComparingFieldByField(krtek);
    }
}