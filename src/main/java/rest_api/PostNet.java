package rest_api;

import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostNet {

    private RestRequest restRequest;
    private URI uri;
    private CookieManager cookieManager;
    private HttpResponse<byte []> httpResponse;

    public PostNet(RestRequest restRequest) {
        this.restRequest = restRequest;
    }


    public PostNet post() {

        HttpClient.Builder httpClientBuilder = HttpClient.newBuilder();
        httpClientBuilder.connectTimeout(restRequest.getConnectTimeout() != null ? Duration.ofSeconds(restRequest.getConnectTimeout()) : Duration.ofSeconds(20));

        // Creating URI
        try {
            uri = new URI(restRequest.getUrl() + restRequest.getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

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
        System.out.println(httpRequest.method());
        System.out.println(httpRequest.uri());
        httpRequest.headers().map().entrySet().forEach(System.out::println);


        // Sending request
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Log
        System.out.println(httpResponse.statusCode());

        return this;
    }

    public Integer getResponseCode() {
        return httpResponse.statusCode();
    }

    public byte [] getBody() {
        return httpResponse.body();
    }


}
