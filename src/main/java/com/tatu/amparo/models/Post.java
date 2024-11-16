package com.tatu.amparo.models;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Setter
    @Id
    private String id;
    @Setter
    private String title;
    @Setter
    @DBRef
    private User creator;
    private String creationDate;
    @Setter
    private List<Comment> comments;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getCreator() {
        return creator;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now().toString();
    }

    public List<Comment> getComments() {
        return comments;
    }


}
