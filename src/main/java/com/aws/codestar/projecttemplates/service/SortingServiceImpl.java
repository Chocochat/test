package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.GatewayResponse;
import com.aws.codestar.projecttemplates.configuration.LambdaEnv;
import com.aws.codestar.projecttemplates.model.SortingRequest;
import com.aws.codestar.projecttemplates.model.SortingResponse;
import com.aws.codestar.projecttemplates.util.HttpUtils;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.aws.codestar.projecttemplates.util.ErrorMsgTransformer.createError;

@Slf4j
@Service
public class SortingServiceImpl implements SortingService {

    private static final String DATA_URL = "/prod/v1/cars";

    @Autowired
    private LambdaEnv lambdaEnv;

    @Autowired
    private HttpUtils httpUtils;

    @Override
    public GatewayResponse getSortedData(SortingRequest sortingRequest, String awsRequestId) {
        final String dataUrl = new StringBuilder().append(lambdaEnv.getDataUrl()).append(DATA_URL).toString();
        JsonObject jsonObject;
        try{
            jsonObject = httpUtils.getResponse(dataUrl, awsRequestId);
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

        return new GatewayResponse(jsonObject.toString(), headers, 200);


//        return jsonObject;
    }
}
