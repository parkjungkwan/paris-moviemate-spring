package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.UserEntity;

import java.util.List;

public interface UserQueryDSL {
    UserEntity findByEmail(String email);
    List<UserEntity> getAll();
    UserEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
    Boolean existsByPassword(String password);
    void insert (UserEntity user);
    void update(UserModel user);
    void updateUserInfo(UserModel userData, String profileImageUrl);

    Boolean exitsByEmail(String email);
}
