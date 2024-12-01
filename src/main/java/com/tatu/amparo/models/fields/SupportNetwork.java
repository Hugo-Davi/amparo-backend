package com.tatu.amparo.models.fields;

import com.tatu.amparo.models.enums.Relation;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupportNetwork {
    private String name;
    private String phoneNumber;
    private Relation relation;

    public SupportNetwork(String name, String phoneNumber, Relation relation) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
    }

}
