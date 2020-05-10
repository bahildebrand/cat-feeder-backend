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
public class CatEatingLambda implements RequestHandler<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(
        CatEatingLambda.class);

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        DBClient client = new DBClient(System.getenv("TABLE_NAME"));
        client.putItem("cat-eating");

        String output = String.format("{ \"message\": \"hello world\" }");
        return new GatewayResponse(output, headers, 200);
    }

}
