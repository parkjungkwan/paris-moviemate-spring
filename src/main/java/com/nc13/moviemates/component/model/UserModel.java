package com.nc13.moviemates.component.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.nc13.moviemates.enums.Provider;
import com.nc13.moviemates.enums.Role;

import groovy.transform.builder.Builder;
import lombok.Data;

import org.aspectj.apache.bcel.classfile.Module.Provide;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Builder
@Component
@Data
public class UserModel {
    private Long id;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String nickname;
    private Role role;
    private String tel;
    private String gender;
    private String profileImageUrl;
    private Provider provider;


}
