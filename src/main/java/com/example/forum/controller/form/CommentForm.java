package com.example.forum.controller.form;

import com.example.forum.repository.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class CommentForm extends Comment {

    @Id
    private int id; // コメントID
    private int reportId; // 元投稿（Content）のID
    private String content; // 返信内容
}
