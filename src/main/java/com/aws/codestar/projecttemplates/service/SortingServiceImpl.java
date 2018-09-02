package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.model.ErrorGatewayResponse;
import com.aws.codestar.projecttemplates.model.GatewayResponse;
import com.aws.codestar.projecttemplates.model.NamesWithCars;
import com.aws.codestar.projecttemplates.utils.CommonUtils;
import com.aws.codestar.projecttemplates.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class SortingServiceImpl implements SortingService {

    //URL to fetch data from external systems
    private static final String DATA_URL = "https://nbdb7q51p5.execute-api.ap-southeast-2.amazonaws.com/prod/v1/cars";

    /**
     * Actual implementation Logic
     * @param awsRequestId
     * @return
     * @throws ErrorGatewayResponse
     */
    @Override
    public GatewayResponse getSortedData(String awsRequestId) throws ErrorGatewayResponse {
        ResponseEntity<String> responseEntity = null;
        int statusCodeValue = 0;
        try {
            responseEntity = getHttpUtils().getResponse(DATA_URL, String.class);
            statusCodeValue = responseEntity.getStatusCodeValue();
        } catch (Exception | ErrorGatewayResponse ex) {
            log.error("Error retrieving data, the status code is {} and the error response is {}", statusCodeValue, responseEntity.getBody());
        }
        if (200 != responseEntity.getStatusCodeValue()) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("Error", "error in getting data");
            throw new ErrorGatewayResponse(new Gson().toJson(errorMap), responseEntity.getHeaders(), statusCodeValue);
        }

        //data received from external system
        JsonArray receivedData = new JsonParser().parse(responseEntity.getBody()).getAsJsonArray();

        //data to clean the empty values
        //Assumption not considering the empty values for sorting
        List<NamesWithCars> cleanedDatas = getCommonUtils().redeivedDataClueanUp(receivedData);

        //sorting by Brand and then by color
        List<NamesWithCars> sortedData = getCommonUtils().doSortingWithBrandAndthencolor(cleanedDatas);
        sortedData.forEach(item->log.debug("-->"+item));

        //Create a simple map for UI
        Map<String, List<String>> mapForUI = getCommonUtils().createSimpleMapWithSortedData(sortedData);
        for (Map.Entry<String, List<String>> entry : mapForUI.entrySet()) {
            log.debug("CarBrand : " + entry.getKey() + " Names : " + entry.getValue());
        }

        return new GatewayResponse(new Gson().toJson(mapForUI), getResponseHeaders(), statusCodeValue);

    }

    protected HttpUtils getHttpUtils() {
        return new HttpUtils();
    }

    static CommonUtils getCommonUtils(){
        return new CommonUtils();
    }

    protected Map<String, String> getResponseHeaders() {
        Map<String, String> responseHeaders = new HashMap<>();
        responseHeaders.put("Content-Type", "application/json");
        return responseHeaders;
    }

    public static void main(String []args) throws ErrorGatewayResponse {
        GatewayResponse responseEntity = new SortingServiceImpl().getSortedData("5678-5678");
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}