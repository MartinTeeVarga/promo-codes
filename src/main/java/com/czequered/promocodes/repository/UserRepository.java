package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.User;
import org.springframework.stereotype.Component;

/**
 * @author Martin Varga
 */
@Component
public class UserRepository extends AbstractDynamoDBRepository<User> {

}
