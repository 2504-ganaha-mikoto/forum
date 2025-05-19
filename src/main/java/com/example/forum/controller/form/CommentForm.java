package com.example.forum.controller.form;

import com.example.forum.repository.entity.Comment;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    @Id
    private int id; // コメントID
    private int reportId; // 元投稿（Content）のID
    private String commentContent; // 返信内容

}
