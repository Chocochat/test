package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.model.ErrorGatewayResponse;
import com.aws.codestar.projecttemplates.model.GatewayResponse;

public interface SortingService {
    GatewayResponse getSortedData(String awsRequestId) throws ErrorGatewayResponse;
}
