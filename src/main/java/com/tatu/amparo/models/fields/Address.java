package com.tatu.amparo.models.fields;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    private String state;
    private String city;
    private String street;
    private String neighborhood;
    private String complement;
}
