package com.aws.codestar.projecttemplates.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ReceivedDataObjectMapper {

    @SerializedName("name")
    @Expose
    @Builder.Default
    public String name="";
    @SerializedName("cars")
    @Expose
    public List<Car> cars = null;

}
