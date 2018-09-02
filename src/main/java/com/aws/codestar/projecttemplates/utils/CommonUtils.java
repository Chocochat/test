package com.aws.codestar.projecttemplates.utils;

import com.aws.codestar.projecttemplates.model.Car;
import com.aws.codestar.projecttemplates.model.ReceivedDataObjectMapper;
import com.aws.codestar.projecttemplates.model.NamesWithCars;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class CommonUtils {

    /**
     * Checks for empty onjects and removes it from processing
     * @param receivedData
     * @return cleanup data from processing
     */
    public List<NamesWithCars> redeivedDataClueanUp(JsonArray receivedData) {

        List<NamesWithCars> cleanedDatas = new ArrayList();
        //iterates each data to check for empty values
        for (JsonElement data: receivedData) {
            ReceivedDataObjectMapper receivedDataObjectMapper = new Gson().fromJson(data.getAsJsonObject(), ReceivedDataObjectMapper.class);
            for (Car car: receivedDataObjectMapper.getCars()) {
                if(!Stream.of(receivedDataObjectMapper.getName(),
                        car.getBrand(),
                        car.getColour()).anyMatch(Strings::isNullOrEmpty)) {
                    NamesWithCars namesWithCars = new NamesWithCars();
                    namesWithCars.setName(receivedDataObjectMapper.getName());
                    namesWithCars.setBrand(car.getBrand());
                    namesWithCars.setColor(car.getColour());
                    cleanedDatas.add(namesWithCars);
                }
            }
        }
        return cleanedDatas;
    }

    /**
     * Sorting by Brand and then by color
     * @param data
     * @return sorted data
     */
    public List<NamesWithCars> doSortingWithBrandAndthencolor(List<NamesWithCars> data) {
        Collections.sort(data, Comparator.comparing(NamesWithCars::getBrand)
                .thenComparing(NamesWithCars::getColor));
        return data;
    }

    /**
     * Creates a simple map with the data for UI
     * @param sortedData
     * @return
     */
    public Map<String, List<String>> createSimpleMapWithSortedData(List<NamesWithCars> sortedData) {
        Map<String, List<String>> mapForUI = new HashMap<>();
        for(NamesWithCars namesWithCars: sortedData){
            if(mapForUI.containsKey(namesWithCars.getBrand())){
                List<String> getExistingList = mapForUI.get(namesWithCars.getBrand());
                getExistingList.add(namesWithCars.getName());
                mapForUI.put(namesWithCars.getBrand(), getExistingList);
            } else {
                List<String> newList = new ArrayList();
                newList.add(namesWithCars.getName());
                mapForUI.put(namesWithCars.getBrand(), newList);
            }
        }
        return mapForUI;
    }
}
