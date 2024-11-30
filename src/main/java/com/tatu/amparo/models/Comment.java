package com.tatu.amparo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Comment {
    private UUID id;
    @DBRef
    private User owner;
    private String text;
    private String commentDate;

    public void setCommentDate() {
        this.commentDate = LocalDateTime.now().toString();
    }
}
