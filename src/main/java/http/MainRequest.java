package http;


import creating_object.StringToObject;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class MainRequest {

    private final Logger logger = LogManager.getLogger(this);

    private int statusCode;
    private Header[] headers;

    private int connectTimeout = 3000;
    private int socketTimeout = 8000;
    private String charset = "UTF-8";


    public void sendRequest(Api api, RequestObject object) {

        if (api.getMethod().name().equals("GET")) {
            throw new RuntimeException("Method 'GET' is not allowed");
        }

        RequestBuilder requestBuilder = RequestBuilder.create(api.getMethod().name());
        requestBuilder.setConfig(RequestConfig.custom().setConnectTimeout(api.getConnectTimeout())
                .setSocketTimeout(api.getSocketTimeout()).build())
                .setUri(api.getUrl());
        api.getHeaders().forEach(requestBuilder::addHeader);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        BasicCookieStore cookieStore = null;

        if (api.getCoocie() != null) {
            cookieStore = new BasicCookieStore();
            cookieStore.addCookie(api.getCoocie());
            httpClientBuilder.setDefaultCookieStore(cookieStore);
        }

        String requestObject = object.getRequestObject();

        if (requestObject != null) {
            requestBuilder.setEntity(new StringEntity(requestObject, api.geCharset()));
        }

        Map<String, String> parameters = object.getRequestParameters();

        if (parameters != null) {
            parameters.forEach(requestBuilder::addParameter);
        }

        if (api.getUrl().startsWith("https")) {
            try {
                httpClientBuilder.setSSLContext(new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        HttpUriRequest request = requestBuilder.build();
        logger.info(creatingRequest(request, cookieStore,
                parameters != null ? parameters.toString() : "", requestObject));

        try (CloseableHttpClient client = httpClientBuilder.build()) {

            CloseableHttpResponse httpResponse = client.execute(request);
            String response = EntityUtils.toString(httpResponse.getEntity());

            logger.info(creatingResponse(httpResponse, response));

            this.statusCode = httpResponse.getStatusLine().getStatusCode();
            this.headers = httpResponse.getAllHeaders();

            //return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String sendRequest(String method,
                              String url,
                              Map<String, String> header,
                              Cookie cookie, Map<String, String> parameters, String requestObject) {


        if (method.equals("GET")) {
            throw new RuntimeException("Method 'GET' is not allowed");
        }

        RequestBuilder requestBuilder = RequestBuilder.create(method);
        requestBuilder.setConfig(RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build())
                .setUri(url);
        header.forEach(requestBuilder::addHeader);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        BasicCookieStore cookieStore = null;

        if (cookie != null) {
            cookieStore = new BasicCookieStore();
            cookieStore.addCookie(cookie);
            httpClientBuilder.setDefaultCookieStore(cookieStore);
        }

        if (requestObject != null) {
            requestBuilder.setEntity(new StringEntity(requestObject, charset));
        }

        if (parameters != null) {
            parameters.forEach(requestBuilder::addParameter);
        }

        if (url.startsWith("https")) {
            try {
                httpClientBuilder.setSSLContext(new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build())
                        .setSSLHostnameVerifier(new NoopHostnameVerifier());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        HttpUriRequest request = requestBuilder.build();
        logger.info(creatingRequest(request, cookieStore,
                parameters != null ? parameters.toString() : "", requestObject));

        try (CloseableHttpClient client = httpClientBuilder.build()) {

            CloseableHttpResponse httpResponse = client.execute(request);
            String response = EntityUtils.toString(httpResponse.getEntity());

            logger.info(creatingResponse(httpResponse, response));

            this.statusCode = httpResponse.getStatusLine().getStatusCode();
            this.headers = httpResponse.getAllHeaders();

            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String creatingRequest(HttpUriRequest request, BasicCookieStore cookieStore, String parameters, String jsonObject) {

        return new StringBuilder().append("\n").append("REQUEST:").append("\n")
                .append("[Headers: ").append(Arrays.toString(request.getAllHeaders())).append("]")
                .append("\n")
                .append("[Cookies: ").append(cookieStore != null ?
                        new ArrayList<>(cookieStore.getCookies()): "").append("]")
                .append("\n")
                .append(request.getRequestLine())
                .append("\n")
                .append(parameters)
                .append("\n")
                .append(jsonObject).toString();
    }

    private String creatingResponse(CloseableHttpResponse httpResponse, String entity) {

        return new StringBuilder().append("\n").append("RESPONSE:").append("\n")
                .append(Arrays.toString(headers)).append("\n")
                .append(httpResponse.getStatusLine())
                .append("\n").append(entity).append("\n").toString();
    }

    public int getResponseCode() {
        return statusCode;
    }

    public String getCookieValue(String cookieName) {

        return Arrays.stream(headers)
                .filter(element -> element.getName().equals("Set-Cookie"))
                .flatMap(arrCookies -> Arrays.stream(arrCookies.getElements()))
                .filter(cookie -> cookie.getName().equals(cookieName))
                .map(HeaderElement::getValue)
                .findFirst()
                .orElse("No cookies found");
    }
}
