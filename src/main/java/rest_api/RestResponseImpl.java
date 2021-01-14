package rest_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

public class RestResponseImpl implements RestResponse {

    private Charset charset;
    private Integer responseCode;
    private Map<String, String> headers;
    private byte [] body;


    public RestResponseImpl(Charset charset, Integer responseCode, Map<String, String> headers, byte [] body) {
        this.charset = charset;
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public <T> T getBodyAs(Class<T> cl) {

        String response = getBody();
        if (response != null && !response.isEmpty()) {
            try {
                return new ObjectMapper().readerFor(cl).readValue(Optional.of(response).get());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    @Override
    public String getBody() {

        String response = new String(body, charset != null ? charset : StandardCharsets.UTF_8);
        System.out.println(response);
        return response;
    }

    @Override
    public RestResponse charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public Integer getResponseCode() {
        return responseCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

}
