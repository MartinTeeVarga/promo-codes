package com.czequered.promocodes.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.czequered.promocodes.model.Code;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Varga
 */
@Component
public class CodeRepository extends AbstractDynamoDBRepository<Code> {

    public Code findByGameIdAndCodeId(String gameId, String codeId) {
        return mapper.load(Code.class, gameId, codeId);
    }

    public List<Code> findByGameId(String gameId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":gameId", new AttributeValue().withS(gameId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("gameId = :gameId")
                .withExpressionAttributeValues(eav);
        PaginatedScanList<Code> scan = mapper.scan(Code.class, scanExpression);
        return scan.stream().collect(Collectors.toList());
    }
}
