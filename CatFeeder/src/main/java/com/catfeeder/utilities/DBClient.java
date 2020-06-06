package com.catfeeder.utilities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

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

        Item item = new Item()
            .withPrimaryKey(PRIMARY_KEY, id, SORT_KEY, dateString);
            // .withNull("event");

        try {
            DynamoDB dynamoDB = new DynamoDB(client);
            Table table = dynamoDB.getTable(tableName);
            table.putItem(item);
        } catch (Exception e) {
            throw e;
        }
    }
}