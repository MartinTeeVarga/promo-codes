package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

/**
 * @author Martin Varga
 */
@DynamoDBTable(tableName = "Game")
public class Game {
    private String id;
    private String details;
    private int codes;

    @Id
    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @DynamoDBAttribute
    public int getCodes() {
        return codes;
    }

    public void setCodes(int codes) {
        this.codes = codes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return id.equals(game.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
