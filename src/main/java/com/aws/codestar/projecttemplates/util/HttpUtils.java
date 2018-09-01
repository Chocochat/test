package com.aws.codestar.projecttemplates.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.aws.codestar.projecttemplates.util.ErrorMsgTransformer.createError;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class HttpUtils {

    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public JsonObject getResponse(String url, String awsRequestId) {
        log.error("URL: {}", url);
        ResponseEntity<String> responseEntity;
            HttpEntity<String> entity = new HttpEntity<>(CreateHeaders());
            RestTemplate restTemplate = getRestTemplate();
            responseEntity = restTemplate.exchange(url, GET, entity, String.class);
            int statusCodeValue = responseEntity.getStatusCodeValue();
            if (200 != statusCodeValue) {
                log.error("Error retrieving data, the status code is {} and the error response is {}", statusCodeValue, responseEntity.getBody());
                throw new RuntimeException(createError(
                        "Exception in backend",
                        statusCodeValue,
                        awsRequestId,
                        "Unable to get required information"));
            }
        log.error("status code: {}",statusCodeValue);
        log.error("Response body : {}", responseEntity.getBody());

        JsonObject jsonObject = new Gson().fromJson(responseEntity.getBody(), JsonObject.class);

        return jsonObject;
    }

    private HttpHeaders CreateHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", String.valueOf(MediaType.APPLICATION_JSON));
        return headers;
    }

}
