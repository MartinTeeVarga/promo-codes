package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.data.annotation.Id;

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

        if (codes != game.codes) return false;
        if (!id.equals(game.id)) return false;
        return details.equals(game.details);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + details.hashCode();
        result = 31 * result + codes;
        return result;
    }
}
