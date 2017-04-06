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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class GameRepositoryTest {

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
    public void findByUserId() {
        saveGame("Krtek", "game1");
        saveGame("Krtek", "game2");
        saveGame("sova", "game3");

        List<Game> all = gameRepository.findByUserId("Krtek");
        assertThat(all).containsOnly(new Game("Krtek", "game1"), new Game("Krtek", "game2"));
    }

    @Test
    public void findByUserIdAndGameId() {
        saveGame("Krtek", "game1");
        saveGame("Krtek", "game2");
        saveGame("sova", "game3");

        gameRepository.findByUserId("Krtek").stream().forEach(g -> {
            System.out.println("g.getUserId() = " + g.getUserId());
            System.out.println("g.getGameId() = " + g.getGameId());
        });

        Game byUserIdAndGameId = gameRepository.findByUserIdAndGameId("Krtek", "game2");
        System.out.println("byUserIdAndGameId.getUserId() = " + byUserIdAndGameId.getUserId());
        System.out.println("byUserIdAndGameId.getGameId() = " + byUserIdAndGameId.getGameId());

        assertThat(gameRepository.findByUserIdAndGameId("Krtek", "game2")).isEqualTo(new Game("Krtek", "game2"));
    }

    private void saveGame(String userId, String gameId) {
        Game game = new Game();
        game.setUserId(userId);
        game.setGameId(gameId);
        game.addAttribute("details", "none");
        gameRepository.save(game);
    }
}