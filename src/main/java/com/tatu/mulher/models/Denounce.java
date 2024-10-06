package com.tatu.mulher.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "denounces")
public class Denounce {
    @Id
    private String id;
    @DBRef
    private User user;

    // Dados da denúncia
    private String name;
}
