package com.czequered.promocodes.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.czequered.promocodes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Martin Varga
 */
@Component
public class UserRepository extends AbstractDynamoDBRepository<User> {
    @Autowired
    public UserRepository(DynamoDBMapper mapper) {
        super(mapper);
    }

    public User findByUserId(String userId) {
        return mapper.load(User.class, userId);
    }
}
