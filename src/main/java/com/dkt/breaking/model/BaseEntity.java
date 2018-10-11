package com.dkt.breaking.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;
    private Boolean deleted = Boolean.FALSE;
}