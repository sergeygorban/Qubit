package rest_api;

import http.Method;
import org.apache.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

public class RestApi {

    private RestRequestImpl restRequest;



    public static RestRequest request() {
        return new RestRequestImpl();
    }



    public static void main(String[] args) {

        String a = "{\"sid\":\"210111ppa1ietfxc1p8r\",\"terms\":\"Выплата процентов в конце срока\"," +
                "\"currency\":980,\"amount\":1.0,\"channel\":\"a24_web\",\"request_ref\":\"Af26DnfRN\",\"client_id\":1005785469,\"date_from\":\"2021-01-06\",\"date_to\":\"2021-02-05\",\"product_code\":\"D000001\"}\n";

        CorpOpenDepositRes corpOpenDepositRes = RestApi.request()
                .method(Method.POST)
                .url("https://proxy-abs.a-bank.com.ua")
                .path("/corp-sc-api/api/v1/open-deposit-corp")
                .headers(Map.of(HttpHeaders.CONTENT_TYPE, "application/json", HttpHeaders.ACCEPT, "application/json"))
                .connectTimeout(8000)
                .httpImplementation(HttpImplementation.NET)
                .requestBody(a)
                .requestParameters(null)
                .send()
                .charset(StandardCharsets.UTF_8)
                .getBodyAs(CorpOpenDepositRes.class);

    }
}
