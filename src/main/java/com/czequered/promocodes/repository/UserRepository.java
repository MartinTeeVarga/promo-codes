package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Martin Varga
 */
public interface UserRepository extends CrudRepository<User, String> {

    @EnableScan
    @EnableScanCount
    Page<User> findAll(Pageable pageable);

}
