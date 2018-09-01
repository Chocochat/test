package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;

import com.aws.codestar.projecttemplates.GatewayResponse;
import com.aws.codestar.projecttemplates.model.SortingRequest;
import com.aws.codestar.projecttemplates.model.SortingResponse;
import com.aws.codestar.projecttemplates.service.SortingService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
public class HelloWorldHandler  extends AbstractLambdaHandler<SortingRequest, Object> {

    final SortingService sortingService;

    public HelloWorldHandler() {
        sortingService = applicationContext.getBean(SortingService.class);
    }

    @Override
    protected Object doHandleRequest(SortingRequest param, Context context) {
        return super.handleRequestWithErrorHandling(param, context);
    }

    @Override
    public Object handleRequest(SortingRequest sortingRequest, Context context) {
        log.error("inputRequest: {}", sortingRequest.toString());
        GatewayResponse gatewayResponse =  sortingService.getSortedData(sortingRequest, context.getAwsRequestId());



        return  gatewayResponse;
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        return new GatewayResponse(new JSONObject().put("Output", "Hello World! singaram").toString(), headers, 200);
    }



//    public Object handleRequest(final Object input, final Context context) {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Content-Type", "application/json");
//        return new GatewayResponse(new JSONObject().put("Output", "Hello World! singaram").toString(), headers, 200);
//    }
}
