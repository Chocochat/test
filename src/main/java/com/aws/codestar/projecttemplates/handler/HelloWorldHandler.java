package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.projecttemplates.model.GatewayResponse;
import com.aws.codestar.projecttemplates.service.SortingService;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for requests to Lambda function. test
 */
@Slf4j
public class HelloWorldHandler implements RequestHandler<Object, Object> {

    protected final SortingService sortingService;


    public HelloWorldHandler(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    public Object handleRequest(Object object, Context context) {
        log.error("inputRequest: {}", object.toString());
        GatewayResponse gatewayResponse =  sortingService.getSortedData(context.getAwsRequestId());



        return  gatewayResponse;
    }



}
