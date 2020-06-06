package com.catfeeder.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import com.catfeeder.utilities.GatewayResponse;

public class AppTest {
  @Test
  public void successfulResponse() {
    FeedCatRest app = new FeedCatRest();
    GatewayResponse result = (GatewayResponse) app.handleRequest(null, null);
    assertEquals(result.getStatusCode(), 200);
    assertEquals(result.getHeaders().get("Content-Type"), "application/json");
    String content = result.getBody();
    assertNotNull(content);
  }
}
