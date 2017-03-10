package com.czequered.promocodes.service;

import com.czequered.promocodes.model.Game;
import com.czequered.promocodes.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Martin Varga
 */
public class GameServiceImplTest {

    private GameService gameService;

    private GameRepository gameRepository;

    private IdGeneratorService idGeneratorService;

    @Before
    public void before() {
        idGeneratorService = mock(IdGeneratorService.class);
        gameRepository = mock(GameRepository.class);
        gameService = new GameServiceImpl(gameRepository, idGeneratorService);
    }

    @Test
    public void getGames() {
        Game game1 = new Game("Krtek", "game1");
        Game game2 = new Game("Krtek", "game2");
        when(gameRepository.findByUserId(eq("Krtek"))).thenReturn(Arrays.asList(game1, game2));
        List<Game> games = gameService.getGames("Krtek");
        assertThat(games).containsOnly(game1, game2);
    }

    @Test
    public void getGame() {
        Game game1 = new Game("Krtek", "game1");
        when(gameRepository.findByUserIdAndGameId(eq("Krtek"), eq("game1"))).thenReturn(game1);
        Game game = gameService.getGame("Krtek", "game1");
        assertThat(game).isEqualTo(game1);
    }

    @Test
    public void deleteGame() {
        gameService.deleteGame("Krtek", "game");
        verify(gameRepository).delete(eq(new Game("Krtek", "game")));
    }

    @Test
    public void saveGame() {
        Game game = new Game("Krtek", "game");
        when(gameRepository.save(eq(game))).thenReturn(game);
        Game saved = gameService.saveGame(game);
        assertThat(game).isEqualTo(saved);
    }

    @Test
    public void saveNewGame() {
        Game game = new Game("Krtek", null);
        when(idGeneratorService.generate()).thenReturn("auticko");
        when(gameRepository.findByUserIdAndGameId(eq("Krtek"), eq("auticko"))).thenReturn(null);
        when(gameRepository.save(any(Game.class))).then(i -> i.getArgumentAt(0, Game.class));
        Game saved = gameService.saveGame(game);
        assertThat(saved.getGameId())
            .isNotNull()
            .isEqualTo("auticko");
    }

    @Test
    public void saveNewGameConflict() {
        Game game = new Game("Krtek", null);
        when(idGeneratorService.generate()).thenReturn("ExistingGameId", "BetterGameId");
        when(gameRepository.findByUserIdAndGameId(eq("Krtek"), eq("ExistingGameId"))).thenReturn(new Game("Krtek", "ExistingGameId"));
        when(gameRepository.findByUserIdAndGameId(eq("Krtek"), eq("BetterGameId"))).thenReturn(null);
        when(gameRepository.save(any(Game.class))).thenAnswer(new Answer<Game>() {
            @Override
            public Game answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return (Game) args[0];
            }
        });
        Game saved = gameService.saveGame(game);
        assertThat(saved.getGameId())
            .isNotNull()
            .isEqualTo("BetterGameId");
    }


}