package com.jumar.user.repository;


import com.jumar.user.models.TokenEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@ActiveProfiles("test")
public class UserTokenRepositoryTest {

    private final UserTokenRepository userTokenRepository;

    private TokenEntity expectedTokenEntity;
    private TokenEntity unsavedTokenEntity;

    @Autowired
    public UserTokenRepositoryTest(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @BeforeEach
    public void setUp() {
        LocalDateTime expiryDate = LocalDateTime.of(2023, 5, 31, 0, 0);
        expectedTokenEntity = TokenEntity
                .builder()
                .id(1)
                .userId(12)
                .token("12345")
                .expiryDate(expiryDate)
                .build();
        userTokenRepository.save(expectedTokenEntity);
        unsavedTokenEntity = TokenEntity
                .builder()
                .id(2)
                .userId(16)
                .token("54321")
                .expiryDate(expiryDate)
                .build();
    }

    @Test
    public void should_return_1_when_deleteByUserId() {
        Integer expectedDeletedEntities = 1;
        Integer actualDeletedEntities = userTokenRepository.deleteByUserId(
                expectedTokenEntity.getUserId());

        assertThat(actualDeletedEntities).isEqualTo(expectedDeletedEntities);
    }

    @Test
    public void should_return_0_whenDeletedByUserId() {
        Integer expectedDeletedEntities = 0;
        Integer actualDeletedEntities = userTokenRepository.deleteByUserId(
                unsavedTokenEntity.getUserId());
        assertThat(actualDeletedEntities).isEqualTo(expectedDeletedEntities);
    }

}
