package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.codestar.projecttemplates.model.ErrorGatewayResponse;
import com.aws.codestar.projecttemplates.model.GatewayResponse;
import com.aws.codestar.projecttemplates.service.SortingService;
import com.aws.codestar.projecttemplates.service.SortingServiceImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler for requests to Lambda function. test
 */
@Slf4j
public class HelloWorldHandler implements RequestHandler<Object, Object> {


    public Object handleRequest(Object object, Context context) {
        log.error("inputRequest: {}", object.toString());
        SortingService sortingService = new SortingServiceImpl();
        GatewayResponse gatewayResponse = null;
        try {
            gatewayResponse = sortingService.getSortedData(context.getAwsRequestId());
        } catch (ErrorGatewayResponse errorGatewayResponse) {
            errorGatewayResponse.printStackTrace();
        }
        return  gatewayResponse;
    }
}
