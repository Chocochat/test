package com.aws.codestar.projecttemplates.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.aws.codestar.projecttemplates.utils.ErrorMsgTransformer.createError;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class HttpUtils {

    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public <T> ResponseEntity<T> getResponse(String url, String awsRequestId, Class<T> responseType) {
        log.error("URL: {}", url);
        ResponseEntity<T> responseEntity;
        HttpEntity<String> entity = new HttpEntity<>(CreateHeaders());
        RestTemplate restTemplate = getRestTemplate();
        responseEntity = restTemplate.exchange(url, GET, entity, responseType);
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



        return responseEntity;
    }

    private HttpHeaders CreateHeaders() {
        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", String.valueOf(MediaType.APPLICATION_JSON));
        return headers;
    }

//    public static void main(String []args){
//        ResponseEntity<String> responseEntity = new HttpUtils().getResponse("https://nbdb7q51p5.execute-api.ap-southeast-2.amazonaws.com/prod/v1/cars", "1234-1234", String.class);
//        System.out.println(responseEntity.getStatusCodeValue());
//        System.out.println(responseEntity.getBody());
//    }

}