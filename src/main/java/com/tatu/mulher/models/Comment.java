package com.tatu.mulher.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;

public class Comment {
    @DBRef
    private User owner;
    private String text;
    private String commentDate;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate() {
        this.commentDate = LocalDate.now().toString();
    }
}
