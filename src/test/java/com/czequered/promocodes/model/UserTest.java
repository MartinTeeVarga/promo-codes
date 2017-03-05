package com.czequered.promocodes.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

/**
 * @author Martin Varga
 */
public class UserTest {
    @Test
    public void equalsTest() throws Exception {
        User fbUser = new User();
        fbUser.setId("facebook-123");
        fbUser.setDetails("hello");

        User ghUser = new User();
        ghUser.setId("github-123");
        ghUser.setDetails("hello");

        User sameFbUser = new User();
        sameFbUser.setId("facebook-123");

        assertThat(fbUser).isEqualTo(sameFbUser);
        assertThat(fbUser).isNotEqualTo(ghUser);
    }

    @Test
    public void hashCodeTest() throws Exception {
        User fbUser = new User();
        fbUser.setId("facebook-123");
        fbUser.setDetails("hello");

        User sameFbUser = new User("facebook-123");

        assertThat(fbUser.hashCode()).isEqualTo(sameFbUser.hashCode());
    }
}