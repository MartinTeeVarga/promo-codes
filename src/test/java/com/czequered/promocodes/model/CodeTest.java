package com.czequered.promocodes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
public class CodeTest {
    @Test
    public void equalsTest() throws Exception {
        Code code1 = new Code();
        code1.setGameId("auticko");
        code1.setCodeId("PUB1");
        code1.setPayload("A");

        Code code2 = new Code();
        code2.setGameId("auticko");
        code2.setCodeId("PUB1");
        code2.setPayload("B");

        assertThat(code1).isEqualTo(code2);
    }

    @Test
    public void hashCodeTest() throws Exception {
        Code code1 = new Code();
        code1.setGameId("auticko");
        code1.setCodeId("PUB1");
        code1.setPayload("A");

        Code id = new Code("auticko", "PUB1");
        assertThat(code1.hashCode()).isEqualTo(id.hashCode());
    }
}