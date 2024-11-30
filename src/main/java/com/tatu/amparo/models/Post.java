package com.tatu.amparo.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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
    private String imageUrl;
    @Setter
    private List<String> content;

    @Setter
    private List<Comment> comments;

    public Post () {}

    public void setCreationDate() {
        this.creationDate = LocalDateTime.now().toString();
    }

}
