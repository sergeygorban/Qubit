package rest_api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class CorpOpenDepositRes {

    @JsonProperty("request_ref")
    private String requestRef;

    @JsonProperty("response_ref")
    private String responseRef;

    private String result;

    @JsonProperty("contract_ref")
    private String contractRef;

    private String timestamp;


}
