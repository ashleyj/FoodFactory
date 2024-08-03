package org.example;

import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

public class Helper {
    public static String baseUri(long port) { return "http://localhost:" + port; }

    public static WebTestClient newWebClient(long port) {
        return WebTestClient
                .bindToServer()
                .baseUrl(baseUri(port))
                .responseTimeout(Duration.ofSeconds(90))
                .build();
    }

}