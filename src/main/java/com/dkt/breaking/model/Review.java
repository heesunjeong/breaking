package com.dkt.breaking.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "reviews")
public class Review extends BaseEntity {
    private String store_id;
    private String user_id;
    private String contents;
}
