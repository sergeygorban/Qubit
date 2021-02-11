package rest_api;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


public class PostNet extends AbstractMethod {

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
        System.out.println(httpRequest.method());
        System.out.println(httpRequest.uri());
        httpRequest.headers().map().entrySet().forEach(System.out::println);



        // Sending request
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            System.out.println(httpResponse.request().headers().toString());
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
