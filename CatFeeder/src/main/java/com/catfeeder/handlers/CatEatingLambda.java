package com.catfeeder.handlers;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catfeeder.utilities.GatewayResponse;
import com.catfeeder.utilities.DBClient;

/**
 * Handler for requests to Lambda function.
 */
public class CatEatingLambda implements RequestHandler<Map<String,String>, Object> {
    private static final Logger logger = LoggerFactory.getLogger(
        CatEatingLambda.class);

    @Override
    public Object handleRequest(Map<String,String> event, final Context context) {
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        DBClient client = new DBClient(System.getenv("TABLE_NAME"));
        try {
            Map<String,String> data = new HashMap<>();
            data.put("event", "cat-fed");
            client.putItem("cat-eating", data);
            String output = String.format("{}");
            return new GatewayResponse(output, headers, 200);
        } catch (Exception e) {
            logger.debug("Failed to add item" + e.toString());
            return new GatewayResponse("{}", headers, 500);
        }
    }

}
