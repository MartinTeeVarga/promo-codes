package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
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
        Game game1 = new Game("krtek", "game1");
        Game game2 = new Game("krtek", "game2");
        when(repository.findByUserId(eq("krtek"))).thenReturn(Arrays.asList(game1, game2));
        List<Game> games = service.getGames("krtek");
        assertThat(games).containsOnly(game1, game2);
    }

    @Test
    public void getGame() {
        Game game1 = new Game("krtek", "game1");
        when(repository.findByUserIdAndGameId(eq("krtek"), eq("game1"))).thenReturn(game1);
        Game game = service.getGame("krtek", "game1");
        assertThat(game).isEqualTo(game1);
    }

    @Test
    public void deleteGame() throws Exception {
        service.deleteGame("krtek", "game");
        verify(repository).delete(eq(new Game("krtek", "game")));
    }

    @Test
    public void saveGame() throws Exception {
        Game game = new Game("krtek", "game");
        when(repository.save(eq(game))).thenReturn(game);
        Game saved = service.saveGame(game);
        assertThat(game).isEqualTo(saved);
    }
}