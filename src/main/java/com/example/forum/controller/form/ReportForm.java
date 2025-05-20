package com.example.forum.controller.form;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
public class ReportForm {

    private int id;
    @NotBlank
    private String content;
//    @CreationTimestamp
//@Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
}
