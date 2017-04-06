package com.czequered.promocodes.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Martin Varga
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDBTable(tableName = "User")
public class User {
    private String id;
    private Map<String, String> attributes;

    /**
     * required for dynamo mapper
     */
    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    @DynamoDBHashKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(String key, String value) {
        if (attributes == null) {
            attributes = new HashMap<>();
        }
        attributes.put(key, value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User other = (User) o;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
