package com.dkt.breaking.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime reg_at;

    @LastModifiedDate
    private LocalDateTime mod_at;

    private Boolean deleted = Boolean.FALSE;
}