package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "quizs-mobilehub-543059164-highScores")

public class HighScoresDO {
    private Double _scoreId;
    private Double _score;
    private String _firstName;
    private String _lasName;

    @DynamoDBHashKey(attributeName = "scoreId")
    @DynamoDBAttribute(attributeName = "scoreId")
    public Double getScoreId() {
        return _scoreId;
    }

    public void setScoreId(final Double _scoreId) {
        this._scoreId = _scoreId;
    }
    @DynamoDBRangeKey(attributeName = "score")
    @DynamoDBAttribute(attributeName = "score")
    public Double getScore() {
        return _score;
    }

    public void setScore(final Double _score) {
        this._score = _score;
    }
    @DynamoDBAttribute(attributeName = "firstName")
    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(final String _firstName) {
        this._firstName = _firstName;
    }
    @DynamoDBAttribute(attributeName = "lasName")
    public String getLasName() {
        return _lasName;
    }

    public void setLasName(final String _lasName) {
        this._lasName = _lasName;
    }

}
