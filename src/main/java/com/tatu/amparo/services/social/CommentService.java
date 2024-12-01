package com.tatu.amparo.services.social;

import com.tatu.amparo.models.fields.Comment;
import com.tatu.amparo.models.collections.User;
import com.tatu.amparo.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private PostRepository postRepository;

    public void postComment(String id, String text, String userId) {

        Comment comment = new Comment();
        comment.setOwner(new User(userId));
        comment.setText(text);
        comment.setId(UUID.randomUUID());
        comment.setCommentDate();

        postRepository.addComment(id, comment);
    }
}
