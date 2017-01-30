package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

/**
 * @author Martin Varga
 */
public class GameCompositeId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gameId;
    private String userId;

    GameCompositeId() {
    }

    public GameCompositeId(String userId, String gameId) {
        this.gameId = gameId;
        this.userId = userId;
    }

    @DynamoDBHashKey
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBRangeKey
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameCompositeId userId = (GameCompositeId) o;

        if (!gameId.equals(userId.gameId)) return false;
        return this.userId.equals(userId.userId);
    }

    @Override
    public int hashCode() {
        int result = gameId.hashCode();
        result = 31 * result + userId.hashCode();
        return result;
    }
}
