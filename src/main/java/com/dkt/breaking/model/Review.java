package com.dkt.breaking.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

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
    @CreatedDate
    private LocalDateTime reg_at;
    @LastModifiedDate
    private LocalDateTime mod_at;
}
