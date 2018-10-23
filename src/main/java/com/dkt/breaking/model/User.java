package com.dkt.breaking.model;

import com.dkt.breaking.configuration.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User extends BaseEntity {

    @NotNull
    private String name;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String gender;
    private String birth;
    private String type;
    private String interface_type;
    @CreatedDate
    private LocalDateTime reg_at;
    @LastModifiedDate
    private LocalDateTime mod_at;
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
