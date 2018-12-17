package http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Map;

public class Post {

    private final Logger logger = LogManager.getLogger(this);
    private int statusCode;
    private int connectTimeout = 3000;
    private int socketTimeout = 8000;
    private String charset = "UTF-8";

    public String sendRequest(String url, Map<String, String> header, String requestObject) {


        HttpUriRequest request = RequestBuilder.create("POST")
                .setConfig(RequestConfig.custom()
                        .setConnectTimeout(connectTimeout)
                        .setSocketTimeout(socketTimeout)
                        .build())
                .setUri(url)
                .setEntity(new StringEntity(requestObject, charset))
                .build();

        header.forEach(request::addHeader);

        logger.info(creatingRequest(request, requestObject));

        try (CloseableHttpClient client = HttpClients.createMinimal()) {

            CloseableHttpResponse httpResponse = client.execute(request);
            String entity = EntityUtils.toString(httpResponse.getEntity());

            logger.info(creatingResponse(httpResponse, entity));

            this.statusCode = httpResponse.getStatusLine().getStatusCode();
            return entity;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getResponseCode() {
        return statusCode;
    }

    private String creatingRequest(HttpUriRequest request, String jsonObject) {

        return new StringBuilder().append("\n").append("REQUEST:").append("\n")
                .append(Arrays.toString(request.getAllHeaders())).append("\n").append(request.getRequestLine())
                .append("\n").append(jsonObject).toString();
    }

    private String creatingResponse(CloseableHttpResponse httpResponse, String entity) {

        return new StringBuilder().append("\n").append("RESPONSE:").append("\n")
                .append(Arrays.toString(httpResponse.getAllHeaders())).append("\n")
                .append(httpResponse.getStatusLine())
                .append("\n").append(entity).append("\n").toString();
    }
}
