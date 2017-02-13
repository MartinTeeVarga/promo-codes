package com.czequered.promocodes.repository;

import com.czequered.promocodes.model.Code;
import com.czequered.promocodes.model.CodeCompositeId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author Martin Varga
 */
public interface CodeRepository extends PagingAndSortingRepository<Code, CodeCompositeId> {

    Code findByGameIdAndCodeId(String gameId, String codeId);

    @EnableScan
    @EnableScanCount
    List<Code> findByGameId(String gameId);

}
