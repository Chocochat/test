package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.model.GatewayResponse;
import com.aws.codestar.projecttemplates.util.HttpUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.aws.codestar.projecttemplates.utils.ErrorMsgTransformer.createError;

//import com.aws.codestar.projecttemplates.configuration.LambdaEnv;

@Slf4j
@Service
public class SortingServiceImpl implements SortingService {

    private static final String DATA_URL = "https://nbdb7q51p5.execute-api.ap-southeast-2.amazonaws.com/prod/v1/cars";


    @Override
    public GatewayResponse getSortedData(String awsRequestId) {
        ResponseEntity<String> responseEntity;
        try{
            responseEntity = new HttpUtils().getResponse(DATA_URL, awsRequestId, String.class);
        } catch (Exception ex) {
            log.error("Not a valid Json received from SAP {}", ex);
            throw new RuntimeException(createError(
                    "Exception in internal server response",
                    HttpStatus.SC_INTERNAL_SERVER_ERROR,
                    awsRequestId,
                    "Internal error"));
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return new GatewayResponse(responseEntity.getBody(), headers, 200);


//        return jsonObject;
    }
}