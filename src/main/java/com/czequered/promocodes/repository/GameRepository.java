package com.czequered.promocodes.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.czequered.promocodes.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Martin Varga
 */
@Component
public class GameRepository extends AbstractDynamoDBRepository<Game> {
    public Game findByUserIdAndGameId(String userId, String gameId) {
        return mapper.load(Game.class, userId, gameId);
    }

    public List<Game> findByUserId(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":userId", new AttributeValue().withS(userId));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("userId = :userId")
                .withExpressionAttributeValues(eav);
        PaginatedScanList<Game> scan = mapper.scan(Game.class, scanExpression);
        return scan.stream().collect(Collectors.toList());
    }
}
