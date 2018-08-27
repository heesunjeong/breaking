package com.dkt.breaking.model;


import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "fileupload")
public class Fileupload extends BaseEntity {
    private String name;
}
