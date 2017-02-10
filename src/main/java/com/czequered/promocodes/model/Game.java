package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

/**
 * @author Martin Varga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "Game")
public class Game {
    @Id
    private GameCompositeId gameCompositeId;
    private String details;

    @DynamoDBHashKey(attributeName = "game")
    public String getGameId() {
        return gameCompositeId != null ? gameCompositeId.getGameId() : null;
    }

    public void setGameId(String gameId) {
        if (gameCompositeId == null) {
            gameCompositeId = new GameCompositeId();
        }
        this.gameCompositeId.setGameId(gameId);
    }

    @DynamoDBRangeKey(attributeName = "code")
    public String getUserId() {
        return gameCompositeId != null ? gameCompositeId.getUserId() : null;
    }

    public void setUserId(String userId) {
        if (gameCompositeId == null) {
            gameCompositeId = new GameCompositeId();
        }
        this.gameCompositeId.setUserId(userId);
    }

    @DynamoDBAttribute
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        return gameCompositeId.equals(game.gameCompositeId);
    }

    @Override public int hashCode() {
        return gameCompositeId.hashCode();
    }
}
