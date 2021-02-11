package rest_api;

import http.Method;

import java.util.Map;

public class RestRequestImpl implements RestRequest {

    private Method method;
    private Integer connectTimeout;
    private Integer socketTimeout;
    private String url;
    private String path;
    private String pathWithParam;
    private Map<String, String> headers;
    private Map<String, String> cookie;
    private Object requestObject;
    private Map<String, Object> requestParameters;
    private HttpImplementation httpImplementation;


    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    @Override
    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getPathWithParam() {
        return pathWithParam;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public Map<String, String> getCookie() {
        return cookie;
    }

    @Override
    public Object getRequestBody() {
        return requestObject;
    }

    @Override
    public Map<String, Object> getRequestParameters() {
        return requestParameters;
    }

    @Override
    public RestRequest method(Method method) {
        this.method = method;
        return this;
    }

    @Override
    public RestRequest connectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    @Override
    public RestRequest socketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    @Override
    public RestRequest url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public RestRequest path(String path) {
        this.path = path;
        return this;
    }

    @Override
    public RestRequest pathWithParam(String pathWithParam) {
        this.pathWithParam = pathWithParam;
        return this;
    }

    @Override
    public RestRequest headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @Override
    public RestRequest cookie(Map<String, String> cookies) {
        this.cookie = cookies;
        return this;
    }

    @Override
    public RestRequest requestBody(Object requestObject) {
        this.requestObject = requestObject;
        return this;
    }

    @Override
    public RestRequest requestParameters(Map<String, Object> requestParameters) {
        this.requestParameters = requestParameters;
        return this;
    }

    @Override
    public RestRequest httpImplementation(HttpImplementation httpImplementation) {
        this.httpImplementation = httpImplementation;
        return this;
    }

    @Override
    public RestResponse send() {

        if (HttpImplementation.NET.equals(httpImplementation)) {

            if (method.equals(Method.POST)) {
                PostNet postNet = new PostNet(this).post();
                return new RestResponseImpl(null, postNet.getResponseCode(), null, postNet.getBody());

            } else {
                throw new RuntimeException("!!! Http method not found !!!");
            }
        }

        return null;
    }

}
