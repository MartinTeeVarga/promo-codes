package com.czequered.promocodes.repository;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.czequered.promocodes.model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class AbstractDynamoDBRepositoryTest {

    GameRepository gameRepository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(Game.class);

        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("access", "secret")))
            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8088", "ap-southeast-2"))
            .build();

        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDB);
        gameRepository = new GameRepository(mapper);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(Game.class);
    }

    @Test
    public void save() throws Exception {
        Game game = new Game();
        game.setUserId("Krtek");
        game.setGameId("auticko");
        game.addAttribute("details", "none");
        Game saved = gameRepository.save(game);
        assertThat(saved).isEqualTo(game);
        assertThat(saved.getAttributes()).isEqualTo(game.getAttributes());
    }

    @Test
    public void delete() throws Exception {
        Game game = new Game();
        game.setUserId("Krtek");
        game.setGameId("auticko");
        game.addAttribute("details", "none");
        Game saved = gameRepository.save(game);
        gameRepository.delete(saved);
        Game found = gameRepository.findByUserIdAndGameId("Krtek", "auticko");
        assertThat(found).isNull();
    }

}