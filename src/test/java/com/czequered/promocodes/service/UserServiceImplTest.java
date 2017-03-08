package com.czequered.promocodes.service;

import com.czequered.promocodes.model.User;
import com.czequered.promocodes.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Martin Varga
 */
public class UserServiceImplTest {

    private UserService service;

    private UserRepository repository;

    @Before
    public void before() {
        repository = mock(UserRepository.class);
        service = new UserServiceImpl(repository);
    }

    @Test
    public void getUser() {
        User krtek = new User("Krtek");
        when(repository.findByUserId(eq("Krtek"))).thenReturn(krtek);
        User retrieved = service.getUser("Krtek");
        assertThat(retrieved).isEqualTo(krtek);
    }

    @Test
    public void saveGame() {
        User krtek = new User("Krtek");
        when(repository.save(eq(krtek))).thenReturn(krtek);
        User saved = service.saveUser(krtek);
        assertThat(saved).isEqualTo(krtek);
    }
}