/*
package rest_api;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class PostApache {

    private RestRequest restRequest;
    private StandardCharsets standardCharsets;
    private BasicCookieStore basicCookieStore;
    private HttpClientBuilder httpClientBuilder;
    //private CloseableHttpResponse httpResponse;
    private HttpUriRequest request;
    //private String httpResponseBody;
    private HttpEntity httpEntity;

    private URI uri;
    private CookieManager cookieManager;
    private HttpRequest.Builder httpRequest;
    private java.net.http.HttpResponse httpResponse;

    public PostApache(RestRequest restRequest) {
        this.restRequest = restRequest;
    }


    public PostApache post() {

        HttpClient.Builder httpClient = HttpClient.newBuilder();
        httpClient.connectTimeout(restRequest.getConnectTimeout() != null ? Duration.ofSeconds(restRequest.getConnectTimeout()) : Duration.ofSeconds(5));

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
            httpClient.cookieHandler(cookieManager);
        }

        // Creating request
        httpRequest = HttpRequest.newBuilder().uri(uri);

        restRequest.getHeaders().forEach(httpRequest::setHeader);
        httpRequest.method(restRequest.getMethod().name(), HttpRequest.BodyPublishers.ofString(restRequest.getRequestBody().toString())).build();

        // Sending request
        try {
            httpResponse = httpClient.build().send(httpRequest.build(), java.net.http.HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return this;

















*/
/*        RequestBuilder requestBuilder =
                RequestBuilder.create(restRequest.getMethod().name())
                        .setConfig(RequestConfig.custom()
                                .setConnectTimeout(restRequest.getConnectTimeout() != null ? restRequest.getConnectTimeout() : 12000)
                                .setSocketTimeout(restRequest.getSocketTimeout() != null ? restRequest.getSocketTimeout() : 12000)
                                .build())
                        .setUri(restRequest.getUrl() + restRequest.getPath());

        httpClientBuilder = HttpClients.custom();

        // Adding headers
        restRequest.getHeaders().forEach(requestBuilder::addHeader);

        if (restRequest.getCookie() != null) {
            basicCookieStore.addCookie(restRequest.getCookie());
            httpClientBuilder.setDefaultCookieStore(basicCookieStore);
        }

        // Adding body to request
        if (restRequest.getRequestBody() != null) {
            requestBuilder.setEntity(new StringEntity(restRequest.getRequestBody().toString(), StandardCharsets.UTF_8));
        }

        // Adding parameters to request
        if (restRequest.getRequestParameters() != null) {
            restRequest.getRequestParameters().forEach((key, val) -> requestBuilder.addParameter(key, val.toString()));
        }

        // Adding SSL certificate
        if (restRequest.getUrl().startsWith("https")) {
            try {
                httpClientBuilder.setSSLContext(new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Sending request
        request = requestBuilder.build();
        try (CloseableHttpClient client = httpClientBuilder.build()) {
            httpResponse = client.execute(request);
            httpEntity = httpResponse.getEntity();
            //httpResponseBody = EntityUtils.toString(httpResponse.getEntity(), responseCharset != null ? responseCharset : StandardCharsets.UTF_8.name());
            return this;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }*//*

    }


    */
/*@Override
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }*//*


    @Override
    public HttpResponse getHttpResponse() {
        return null;
    }

    @Override
    public HttpEntity getHttpEntity() {
        return httpEntity;
    }
}
*/
