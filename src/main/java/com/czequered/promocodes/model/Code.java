package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@DynamoDBTable(tableName = "Code")
public class Code implements Serializable {
    @Id
    private CodeId codeId;
    private String from;
    private String to;
    private Boolean pub;
    private String payload;

    @DynamoDBHashKey(attributeName = "game")
    public String getGame() {
        return codeId != null ? codeId.getGame() : null;
    }

    public void setGame(String game) {
        if (codeId == null) {
            codeId = new CodeId();
        }
        this.codeId.setGame(game);
    }

    @DynamoDBRangeKey(attributeName = "code")
    public String getCode() {
        return codeId != null ? codeId.getCode() : null;
    }

    public void setCode(String code) {
        if (codeId == null) {
            codeId = new CodeId();
        }
        this.codeId.setCode(code);

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

        Code code = (Code) o;

        if (!codeId.equals(code.codeId)) return false;
        if (!from.equals(code.from)) return false;
        if (!to.equals(code.to)) return false;
        if (!pub.equals(code.pub)) return false;
        return payload.equals(code.payload);

    }

    @Override
    public int hashCode() {
        int result = codeId.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + pub.hashCode();
        result = 31 * result + payload.hashCode();
        return result;
    }
}
