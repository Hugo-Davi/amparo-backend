package com.tatu.amparo.models;

import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;

public class Comment {
    @Setter
    @DBRef
    private User owner;
    @Setter
    private String text;
    private String commentDate;

    public User getOwner() {
        return owner;
    }

    public String getText() {
        return text;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate() {
        this.commentDate = LocalDate.now().toString();
    }
}
