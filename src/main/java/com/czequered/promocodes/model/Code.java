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
@DynamoDBTable(tableName = "Code")
public class Code {
    @Id
    private CodeCompositeId codeCompositeId;
    private String from;
    private String to;
    private Boolean pub;
    private String payload;

    @DynamoDBHashKey(attributeName = "game")
    public String getGameId() {
        return codeCompositeId != null ? codeCompositeId.getGameId() : null;
    }

    public void setGameId(String gameId) {
        if (codeCompositeId == null) {
            codeCompositeId = new CodeCompositeId();
        }
        this.codeCompositeId.setGameId(gameId);
    }

    @DynamoDBRangeKey(attributeName = "code")
    public String getCodeId() {
        return codeCompositeId != null ? codeCompositeId.getCodeId() : null;
    }

    public void setCodeId(String codeId) {
        if (codeCompositeId == null) {
            codeCompositeId = new CodeCompositeId();
        }
        this.codeCompositeId.setCodeId(codeId);

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

        return codeCompositeId.equals(code.codeCompositeId);

    }

    @Override
    public int hashCode() {
        return codeCompositeId.hashCode();
    }
}
