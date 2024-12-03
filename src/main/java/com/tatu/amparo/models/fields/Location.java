package com.tatu.amparo.models.fields;

import com.tatu.amparo.models.enums.TypeLocation;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Location {
    private TypeLocation type;
    private ArrayList<Double> coordinates;
}
