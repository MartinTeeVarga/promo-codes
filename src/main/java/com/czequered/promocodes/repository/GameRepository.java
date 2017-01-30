package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.Game;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Martin Varga
 */
public interface GameRepository extends PagingAndSortingRepository<Game, String> {

    Game findByGameId(String gameId);

    @EnableScan
    @EnableScanCount
    Page<Game> findAll(Pageable pageable);
}
