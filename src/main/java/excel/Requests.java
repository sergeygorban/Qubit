package excel;

import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 20.03.2018.
 */
public class Requests {

    private static String AUTHORIZATION_ID = "180517PLgiqmolo315bw";
    private static ContentType CONTENT_TYPE = ContentType.APPLICATION_JSON;

    private static int requestTimeout = 4000;
    private static int connectTimeout = 3000;
    private static int socketTimeout = 8000;

    public static List<String> sendRequestNew(Api api, Map<String,Object> body, String filePath) {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(requestTimeout)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .build();


        RequestBuilder request =
                RequestBuilder.create(api.method
                        .substring(0,api.method.indexOf("-") > 0 ? api.method.indexOf("-") : api.method.length()))
                        .setConfig(requestConfig)
                        .setUri(api.url)
                        //.setHeader(HttpHeaders.AUTHORIZATION, AUTHORIZATION_ID)
                        .setHeader(HttpHeaders.CONTENT_TYPE, CONTENT_TYPE.toString());


        if (api.method.startsWith("GET")) {

            if (api.method.equals("GET-WITHOUT_PARAM")) {

                request.setUri(api.url + "/" + body.entrySet().stream()
                        .map(elem -> new Gson().fromJson(elem.getValue().toString(), String.class))
                        .findFirst()
                        .orElse("")
                );

            } else {
                body.forEach((key, value) -> request.addParameter(key, new Gson()
                        .fromJson(value.toString(),String.class)));
            }
        }

        if (api.method.contains("MULT")) {

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("req", new StringBody(new Gson().toJson(body),CONTENT_TYPE));
            builder.addPart("file", new FileBody(new File(filePath), CONTENT_TYPE));

            request.removeHeaders(HttpHeaders.CONTENT_TYPE);
            request.setEntity(builder.build());
        }

        if (!api.method.startsWith("GET") && !api.method.contains("MULT") ) {

            request.setEntity(new StringEntity(new Gson().toJson(body), "UTF-8"));
        }

        HttpUriRequest uriRequest = request.build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(new SSLContextBuilder().
                loadTrustMaterial(null, (certificate, authType) -> true)
                .build()).setSSLHostnameVerifier(new NoopHostnameVerifier()).build()) {

            CloseableHttpResponse response = httpClient.execute(uriRequest);

            String req = "REQUEST:" + "\n" + uriRequest.getRequestLine() + "\n" + "Headers: "
                    + Arrays.toString(uriRequest.getAllHeaders())
                    + "\n" + (request.getEntity() != null ? EntityUtils.toString(request.getEntity()) : "");

            String res = "RESPONSE:" + "\n" + response.getStatusLine() + "\n"
                    + (response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : "") + "\n" + "";

            System.out.println(req + "\n"  + res );

            return Arrays.asList(req, res);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void setRequestTimeout(int requestTimeout) {
        requestTimeout = requestTimeout;
    }

    public static void setConnectTimeout(int connectTimeout) {
        connectTimeout = connectTimeout;
    }

    public static void setSocketTimeout(int socketTimeout) {
        socketTimeout = socketTimeout;
    }
}
