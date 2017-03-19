package com.czequered.promocodes.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

        assertThat(fbUser).isEqualTo(fbUser);
        assertThat(fbUser).isEqualTo(sameFbUser);
        assertThat(sameFbUser).isEqualTo(fbUser);
        assertThat(fbUser).isNotEqualTo(ghUser);
        assertThat(fbUser).isNotEqualTo(null);
        assertThat(fbUser).isNotEqualTo("facebook-123");
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