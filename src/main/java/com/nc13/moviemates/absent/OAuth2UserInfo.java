package com.nc13.moviemates.absent;

import lombok.Builder;
import java.util.*;

import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.enums.Provider;
import com.nc13.moviemates.enums.Role;

import jakarta.security.auth.message.AuthException;




@Builder
public record OAuth2UserInfo(
        String nickname,
        String email,
        String profileImageUrl,
        Provider provider
) {

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            case "kakao" -> ofKakao(attributes);
            // default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
            default -> null;
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .nickname((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .profileImageUrl((String) attributes.get("profileImageUrl"))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .nickname((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profileImageUrl((String) profile.get("profile_image_url"))
                .build();
    }

    public UserEntity toEntity() {
        return UserEntity.builder()
                .nickname(nickname)
                .email(email)
                .role(Role.USER)
                .build();
    }
}