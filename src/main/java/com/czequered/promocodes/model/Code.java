package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "code")
public class Code {
    private String game;
    private String code;
    private String from;
    private String to;
    private Boolean pub;
    private String payload;

    @DynamoDBHashKey(attributeName = "game")
    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @DynamoDBRangeKey(attributeName = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Code code1 = (Code) o;

        if (!game.equals(code1.game)) return false;
        if (!code.equals(code1.code)) return false;
        if (from != null ? !from.equals(code1.from) : code1.from != null) return false;
        if (to != null ? !to.equals(code1.to) : code1.to != null) return false;
        if (pub != null ? !pub.equals(code1.pub) : code1.pub != null) return false;
        return payload != null ? payload.equals(code1.payload) : code1.payload == null;

    }

    @Override
    public int hashCode() {
        int result = game.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (pub != null ? pub.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}
