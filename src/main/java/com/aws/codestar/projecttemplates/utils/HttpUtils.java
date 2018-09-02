package com.aws.codestar.projecttemplates.utils;

import com.aws.codestar.projecttemplates.model.ErrorGatewayResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
public class HttpUtils {

    /**
     * Gets new RestTemplate
     * @return
     */
    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * Makes Http call
     * @param url
     * @param responseType
     * @param <T>
     * @return responseEntity
     */
    public <T> ResponseEntity<T> getResponse(String url, Class<T> responseType) throws ErrorGatewayResponse {
        log.debug("URL: {}", url);

        //Makes Get request
        ResponseEntity<T> responseEntity = null;
        
        try{
            responseEntity = getRestTemplate().exchange(url, GET, new HttpEntity<>(new HttpHeaders()) , responseType);
        } catch (Exception ex) {
            log.error("Error retrieving data, the error response is {}", ex);
            if (200 != ((HttpClientErrorException)ex).getRawStatusCode()) {
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("Error", "error in getting data");
                throw new ErrorGatewayResponse(new Gson().toJson(errorMap), ((HttpClientErrorException) ex).getResponseHeaders(), ((HttpClientErrorException)ex).getRawStatusCode());
            }
        }
        return responseEntity;
    }

    public static void main(String []args) throws ErrorGatewayResponse {
        ResponseEntity<String> responseEntity ;
            responseEntity = new HttpUtils().getResponse("https://nbdb7q51p5.execute-api.ap-southeast-2.amazonaws.com/prod/v1/carsl", String.class);
    }

}