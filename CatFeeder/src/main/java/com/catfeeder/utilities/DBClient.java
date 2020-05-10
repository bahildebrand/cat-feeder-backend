package com.catfeeder.utilities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBClient {
    private AmazonDynamoDB client;
    private String tableName;
    private final String PRIMARY_KEY = "id";
    private final String SORT_KEY = "timeStamp";

    public DBClient(String tableName) {
        this.tableName = tableName;
        this.client = AmazonDynamoDBClientBuilder.standard().build();
    }

    public void putItem(String id) {
        String dateString = new Date().toString();
        Map<String, AttributeValue> item = new HashMap<>();

        item.put(PRIMARY_KEY, new AttributeValue("cat-eating"));
        item.put(SORT_KEY, new AttributeValue(dateString));

        PutItemRequest request = new PutItemRequest()
            .withTableName(tableName)
            .withItem(item);

        client.putItem(request);
    }
}