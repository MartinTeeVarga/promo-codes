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
        Game game = new Game();
        game.setUserId("krtek");
        game.setGameId("game");
        game.setDetails("{}");
        repository.save(game);

        List<Game> all = repository.findByUserId("krtek");
        assertThat(all).containsExactly(game);
    }
}