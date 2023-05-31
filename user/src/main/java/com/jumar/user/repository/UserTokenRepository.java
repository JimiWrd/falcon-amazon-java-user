package com.jumar.user.repository;

import com.jumar.user.models.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserTokenRepository  extends JpaRepository<TokenEntity, Integer> {

    String USER_ID = "user_id";
    Integer deleteByUserId(@Param(USER_ID) Integer userID);
}
