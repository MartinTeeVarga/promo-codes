package com.czequered.promocodes.service;

import com.czequered.promocodes.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;

/**
 * @author Martin Varga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameServiceImplTest {

    private GameService service;

    private GameRepository repository;

    @Before
    public void before() {
        repository = mock(GameRepository.class);
        service = new GameServiceImpl(repository);
    }

    @Test
    public void getGames() throws Exception {
    }

    @Test
    public void getGame() {
    }

    @Test
    public void deleteGame() throws Exception {
    }

    @Test
    public void saveGame() throws Exception {
    }
}