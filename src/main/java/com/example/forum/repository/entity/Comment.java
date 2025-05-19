package com.example.forum.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // コメントID
    @Column
    private int reportId; // 元投稿（Content）のID
    @Column
    private String commentContent; // 返信内容
    @CreationTimestamp
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
