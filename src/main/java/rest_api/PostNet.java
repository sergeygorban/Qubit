package rest_api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PostNet extends AbstractMethod {

    private static final Logger logger = LogManager.getLogger();

    private RestRequest restRequest;
    private CookieManager cookieManager;
    private HttpResponse<byte []> httpResponse;
    private URI uri;

    public PostNet(RestRequest restRequest) {
        this.restRequest = restRequest;
    }


    public PostNet post() {

        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder();
        httpClientBuilder.connectTimeout(restRequest.getConnectTimeout() != null ? Duration.ofSeconds(restRequest.getConnectTimeout()) : Duration.ofSeconds(20));

        // Creating URI
        uri = createURI(restRequest);

        // Creating cookies
        if (restRequest.getCookie() != null) {
            cookieManager = new CookieManager();
            restRequest.getCookie().forEach((key, value) -> cookieManager.getCookieStore().add(uri, new HttpCookie(key, value)));
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            httpClientBuilder.cookieHandler(cookieManager);
        }

        // Creating request
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder().uri(uri);
        restRequest.getHeaders().forEach(httpRequestBuilder::setHeader);
        httpRequestBuilder.method(restRequest.getMethod().name(), HttpRequest.BodyPublishers.ofString(restRequest.getRequestBody().toString()));
        HttpRequest httpRequest = httpRequestBuilder.build();
        HttpClient httpClient = httpClientBuilder.build();

        // Log
        logger.info("REQUEST: ");
        logger.info("Method: " + httpRequest.method());
        logger.info("URL: " + httpRequest.uri());
        httpRequest.headers().map().forEach((key, value) -> logger.info("HTTP Header: " + key + " = " + value.get(0)));
        restRequest.getCookie().forEach((key, value) -> logger.info("HTTP Cookie: " + key + " = " + value));
        logger.info("Request Body: " + restRequest.getRequestBody() + "\n");


        // Sending request
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Log
        logger.info("RESPONSE: ");
        logger.info("HTTP Status: " + httpResponse.statusCode());
        return this;
    }

    public Integer getResponseCode() {
        return httpResponse.statusCode();
    }

    public byte [] getBody() {
        return httpResponse.body();
    }


}
