package com.catfeeder.handlers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import com.catfeeder.handlers.CatEatingLambda;
import com.catfeeder.utilities.GatewayResponse;

public class CatEatingLambdaTest {
    @Test
    public void successfulResponse() {
        CatEatingLambda handler = new CatEatingLambda();
        Map<String,String> event = new HashMap<>();
        event.put("event", "cat-fed");

        GatewayResponse result = (GatewayResponse) handler.handleRequest(
            event, null);
            // assertEquals(result.getStatusCode(), 200);
    }
}
