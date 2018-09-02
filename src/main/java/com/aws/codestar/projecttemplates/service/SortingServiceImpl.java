package com.aws.codestar.projecttemplates.service;

import com.aws.codestar.projecttemplates.model.Car;
import com.aws.codestar.projecttemplates.model.Example;
import com.aws.codestar.projecttemplates.model.GatewayResponse;
import com.aws.codestar.projecttemplates.model.NamesWithCars;
import com.aws.codestar.projecttemplates.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
        JsonArray inputObject = new JsonParser().parse(responseEntity.getBody()).getAsJsonArray();
        ArrayList<NamesWithCars> list = new ArrayList();

        for (JsonElement jsonElement: inputObject) {

            Example example = new Gson().fromJson(jsonElement.getAsJsonObject(), Example.class);
            for (Car car:example.getCars()) {
                NamesWithCars namesWithCars = new NamesWithCars();
                namesWithCars.setName(example.getName());
                namesWithCars.setBrand(car.getBrand());
                namesWithCars.setColor(car.getColour());
                list.add(namesWithCars);
            }
        }

        Collections.sort(list, Comparator.comparing(NamesWithCars::getBrand)
                .thenComparing(NamesWithCars::getColor));
        log.error("-- with thenComparing --");

        list.forEach(item->log.error(""+item));
        Map<String, List<String>> map = new HashMap<>();

        for(NamesWithCars namesWithCars: list){
            if(map.containsKey(namesWithCars.getBrand())){
                List<String> list1 = map.get(namesWithCars.getBrand());
                list1.add(namesWithCars.getName());
                map.put(namesWithCars.getBrand(), list1);
            } else{
                List<String> list1 = new ArrayList();
                list1.add(namesWithCars.getName());
                map.put(namesWithCars.getBrand(), list1);
            }
        }

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            log.error("Item : " + entry.getKey() + " Count : " + entry.getValue());
        }


        return new GatewayResponse(new Gson().toJson(map), headers, 200);


//        return jsonObject;
    }

    public static void main(String []args){
        GatewayResponse responseEntity = new SortingServiceImpl().getSortedData("5678-5678");
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
    }
}