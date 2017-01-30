package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Martin Varga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository repository;

    @ClassRule
    public static final LocalDynamoDBCreationRule dynamoDBProvider = new LocalDynamoDBCreationRule();

    @Before
    public void before() {
        dynamoDBProvider.createTable(User.class);
    }

    @After
    public void after() {
        dynamoDBProvider.deleteTable(User.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllTest() {
        repository.findAll();
    }

    @Test
    public void findAllPageableTest() {
        User user = new User();
        user.setId("krtek");
        user.setGames(15);
        user.setDetails("{}");
        repository.save(user);

        Page<User> all = repository.findAll(new PageRequest(0, 1));
        assertThat(all).containsExactly(user);
    }
}