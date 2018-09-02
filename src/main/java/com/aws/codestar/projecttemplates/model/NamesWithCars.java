package com.aws.codestar.projecttemplates.model;

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
public class NamesWithCars {

    @Builder.Default
    private String brand="";
    @Builder.Default
    private String color="";
    private String name;


}
