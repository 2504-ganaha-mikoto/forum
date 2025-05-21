package com.example.forum.controller.form;

import com.example.forum.repository.entity.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    @Id
    private int commentId; // コメントID
    private int reportId; // 元投稿
    @NotBlank(message = "コメントを入力してください")
    private String commentContent; // 返信内容
    private String updatedDate;
    private String createdDate;
}
