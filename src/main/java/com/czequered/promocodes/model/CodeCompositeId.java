package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

/**
 * @author Martin Varga
 */
public class CodeCompositeId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String gameId;
    private String codeId;

    CodeCompositeId() {
    }

    public CodeCompositeId(String gameId, String codeId) {
        this.gameId = gameId;
        this.codeId = codeId;
    }

    @DynamoDBHashKey
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBRangeKey
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeCompositeId codeCompositeId = (CodeCompositeId) o;

        if (!gameId.equals(codeCompositeId.gameId)) return false;
        return codeId.equals(codeCompositeId.codeId);

    }

    @Override
    public int hashCode() {
        int result = gameId.hashCode();
        result = 31 * result + codeId.hashCode();
        return result;
    }
}
