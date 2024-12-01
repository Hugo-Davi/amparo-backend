package com.tatu.amparo.models.fields;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Location {
    private String type;
    private ArrayList<Double> coordinates;
}
