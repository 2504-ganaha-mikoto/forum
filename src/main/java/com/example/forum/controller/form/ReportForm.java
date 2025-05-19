package com.example.forum.controller.form;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    private String content;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
