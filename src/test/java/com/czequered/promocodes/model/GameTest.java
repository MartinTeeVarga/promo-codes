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
        game1.setGameId("auticko");
        game1.setUserId("Krtek");
        game1.setDetails("{}");

        Game game2 = new Game();
        game2.setGameId("auticko");
        game2.setUserId("Krtek");
        game2.setDetails("Not empty");

        assertThat(game1).isEqualTo(game2);
        assertThat(game1).isEqualTo(game2);
        assertThat(game2).isEqualTo(game1);
    }

    @Test
    public void doesNotEqualTest() {
        Game game1 = new Game();
        game1.setGameId("auticko");
        game1.setUserId("Krtek");
        game1.setDetails("{}");

        Game game2 = new Game();
        game2.setGameId("kalhoty");
        game2.setUserId("Krtek");
        game2.setDetails("Not empty");

        Game game3 = new Game();
        game3.setGameId("kalhoty");
        game3.setUserId("Sova");
        game3.setDetails("Not empty");

        assertThat(game1).isNotEqualTo(game2);
        assertThat(game2).isNotEqualTo(game3);
        assertThat(game1).isNotEqualTo(new User("Krtek"));
    }

    @Test
    public void hashCodeTest() throws Exception {
        Game game1 = new Game();
        game1.setGameId("auticko");
        game1.setUserId("Krtek");
        game1.setDetails("A");

        Game id = new Game("Krtek", "auticko");
        assertThat(game1.hashCode()).isEqualTo(id.hashCode());
    }
}