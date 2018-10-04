package com.dkt.breaking.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Document(collection = "reviews")
public class Review extends BaseEntity {

    @NotNull
    private String storeId;
    private String userId;
    private String contents;
    private String userName;
}
