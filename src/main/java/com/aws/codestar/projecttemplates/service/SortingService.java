package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.GatewayResponse;
import com.aws.codestar.projecttemplates.model.SortingRequest;
import com.aws.codestar.projecttemplates.model.SortingResponse;

public interface SortingService {

    GatewayResponse getSortedData(SortingRequest sortingRequest, String awsRequestId);
}
