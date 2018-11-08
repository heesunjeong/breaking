package com.dkt.breaking.model;

import com.dkt.breaking.configuration.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @Field("_id")
    private String id;
    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    private String password;
    private String gender;
    private String birth;
    private String type;
    private String interface_type;
    @CreatedDate
    private LocalDateTime reg_at;
    @LastModifiedDate
    private LocalDateTime mod_at;
    private Boolean deleted = Boolean.FALSE;
    @JsonIgnore
    private Set<UserRole> roles;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
