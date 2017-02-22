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
@DynamoDBTable(tableName = "Code")
public class Code {
    private String gameId;
    private String codeId;
    private String from;
    private String to;
    private Boolean pub;
    private String payload;

    /**
     * For mapper
     */
    public Code() {
    }

    public Code(String gameId, String codeId) {
        this.gameId = gameId;
        this.codeId = codeId;
    }

    @DynamoDBHashKey(attributeName = "gameId")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBRangeKey(attributeName = "codeId")
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    @DynamoDBAttribute(attributeName = "from")
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @DynamoDBAttribute(attributeName = "to")
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @DynamoDBAttribute(attributeName = "pub")
    public Boolean getPub() {
        return pub;
    }

    public void setPub(Boolean pub) {
        this.pub = pub;
    }

    @DynamoDBAttribute(attributeName = "payload")
    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;

        Code code = (Code) o;

        if (!gameId.equals(code.gameId)) return false;
        return codeId.equals(code.codeId);
    }

    @Override public int hashCode() {
        return Objects.hash(gameId, codeId);
    }
}
