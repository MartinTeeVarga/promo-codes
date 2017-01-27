package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

public class CodeId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String game;
    private String code;

    @DynamoDBHashKey
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @DynamoDBRangeKey
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodeId codeId = (CodeId) o;

        if (!game.equals(codeId.game)) return false;
        return code.equals(codeId.code);

    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }
}
