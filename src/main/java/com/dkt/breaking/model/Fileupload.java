package com.dkt.breaking.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "fileupload")
public class Fileupload {
    @Id
    private String id;
    private String name;
    private Boolean deleted = Boolean.FALSE;
}
