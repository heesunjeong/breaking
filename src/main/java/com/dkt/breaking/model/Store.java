package com.dkt.breaking.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "stores")
public class Store {
    @Id
    private String id;
    private String name;
    private String address;
    private String storeKey;
    private String category;
    private String phone;
    private String x;
    private String y;

    public Store(String storeKey) {
        this.storeKey = storeKey;
    }
}
