package lambdas;


import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.GatewayResponse;

/**
 * Handler for requests to Lambda function.
 */
public class CatEatingLambda implements RequestHandler<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        String output = String.format("{ \"message\": \"hello world\" }");
        return new GatewayResponse(output, headers, 200);
    }

}
