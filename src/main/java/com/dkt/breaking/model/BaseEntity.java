package com.dkt.breaking.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    @CreatedDate
    private Date reg_at;

    @LastModifiedDate
    private Date mod_at;

    private Boolean deleted = Boolean.FALSE;

}