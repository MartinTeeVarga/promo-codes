package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.Game;
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
public class GameRepositoryTest {

    @Autowired
    GameRepository repository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(Game.class);

    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(Game.class);
    }

    @Test
    public void findByUserId() {
        saveGame("krtek", "game1");
        saveGame("krtek", "game2");
        saveGame("sova", "game3");

        List<Game> all = repository.findByUserId("krtek");
        assertThat(all).containsOnly(new Game("krtek", "game1"), new Game("krtek", "game2"));
    }

    @Test
    public void findByUserIdAndGameId() {
        saveGame("krtek", "game1");
        saveGame("krtek", "game2");
        saveGame("sova", "game3");

        repository.findByUserId("krtek").stream().forEach(g -> {
            System.out.println("g.getUserId() = " + g.getUserId());
            System.out.println("g.getGameId() = " + g.getGameId());
        });

        Game byUserIdAndGameId = repository.findByUserIdAndGameId("krtek", "game2");
        System.out.println("byUserIdAndGameId.getUserId() = " + byUserIdAndGameId.getUserId());
        System.out.println("byUserIdAndGameId.getGameId() = " + byUserIdAndGameId.getGameId());

        assertThat(repository.findByUserIdAndGameId("krtek", "game2")).isEqualTo(new Game("krtek", "game2"));
    }

    private void saveGame(String userId, String gameId) {
        Game game = new Game();
        game.setUserId(userId);
        game.setGameId(gameId);
        game.setDetails("{}");
        repository.save(game);
    }
}