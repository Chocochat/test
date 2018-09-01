package com.aws.codestar.projecttemplates.model;

import com.aws.codestar.projecttemplates.GatewayResponse;

public interface SortingService {
    GatewayResponse getSortedData(String awsRequestId);
}
