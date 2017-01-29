package com.czequered.promocodes.repository;

import com.czequered.promocodes.LocalDynamoDBCreationRule;
import com.czequered.promocodes.model.Game;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository repository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Test(expected = IllegalArgumentException.class)
    public void findAllTest() {
        repository.findAll();
    }

    @Test
    public void findAllPageableTest() {
        Game game = new Game();
        game.setId("test");
        game.setCodes(15);
        game.setDetails("{}");
        repository.save(game);

        Page<Game> all = repository.findAll(new PageRequest(0, 1));
        assertThat(all).containsExactly(game);
    }
}