package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * @author Martin Varga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "Game")
public class Game {
    private String userId;
    private String gameId;
    private String details;

    public Game() {
    }

    public Game(String userId, String gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    @DynamoDBHashKey(attributeName = "gameId")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBRangeKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

        if (!userId.equals(game.userId)) return false;
        return gameId.equals(game.gameId);
    }

    @Override public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}
