package com.czequered.promocodes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class GameTest {
    @Test
    public void equalsTest() throws Exception {
        Game game1 = new Game();
        game1.setGameId("test");
        game1.setUserId("Joe");
        game1.setDetails("{}");

        Game game2 = new Game();
        game2.setGameId("test");
        game2.setUserId("Joe");
        game2.setDetails("Not empty");

        assertThat(game1).isEqualTo(game2);
    }

    @Test
    public void hashGameTest() throws Exception {
        Game game1 = new Game();
        game1.setGameId("test");
        game1.setUserId("Joe");
        game1.setDetails("A");

        Game id = new Game("Joe", "test");
        assertThat(game1.hashCode()).isEqualTo(id.hashCode());
    }
}