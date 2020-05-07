package helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.nio.ByteBuffer;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.iotdata.AWSIotDataClientBuilder;
import com.amazonaws.services.iotdata.AWSIotData;
import com.amazonaws.services.iotdata.model.PublishRequest;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<Object, Object> {

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        AWSIotData iotData = AWSIotDataClientBuilder.standard()
                .withEndpointConfiguration(new EndpointConfiguration("ao28sb0mkuqhc-ats.iot.us-east-1.amazonaws.com", 
                        "us-east-1"))
                .build();


        try {
            String payload = "{ \"feed\": 1 }";
            ByteBuffer payloadBB = ByteBuffer.wrap(payload.getBytes());
            PublishRequest pubRequest = new PublishRequest().withQos(1)
                                                            .withTopic("cat-feeder/feed")
                                                            .withPayload(payloadBB);
            iotData.publish(pubRequest);
        } catch(Exception e) {
            LambdaLogger logger = context.getLogger();
            logger.log("Publish fail: " + e.toString());
        }

        try {
            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);
            return new GatewayResponse(output, headers, 200);
        } catch (IOException e) {
            return new GatewayResponse("{}", headers, 500);
        }
    }

    private String getPageContents(String address) throws IOException{
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
