package com.czequered.promocodes.repositories;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.model.CodeId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CodeRepository extends PagingAndSortingRepository<Code, CodeId> {

    Code findByGameAndCode(String game, String code);

    @EnableScan
    @EnableScanCount
    Page<Code> findAll(Pageable pageable);
}
