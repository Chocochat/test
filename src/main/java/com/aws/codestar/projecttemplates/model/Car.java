package com.aws.codestar.projecttemplates.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    @SerializedName("brand")
    @Expose
    @Builder.Default
    public String brand="";
    @SerializedName("colour")
    @Expose
    @Builder.Default
    public String colour="";

}
