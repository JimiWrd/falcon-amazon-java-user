package com.jumar.user.repository;

import com.jumar.user.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class UserRepositoryTest {

    private final UserRepository userRepository;
    private User testUser;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUpRepository() {
        testUser = User.builder()
                .id(1)
                .forenames("Josh")
                .surname("Wood")
                .emailAddress("josh.wood@me.com")
                .telephone("0121123456")
                .dateOfBirth(LocalDate.of(1994, 4, 2))
                .username("josh.wood@me.com")
                .passwordHash("8372jhljfre89u7w2")
                .dateAdded(LocalDateTime.of(2023, 5, 12, 0,0))
                .dateLastModified(LocalDateTime.of(2023, 5, 12, 0,0))
                .failedLoginAttempts(0)
                .isDeleted(false)
                .build();
        userRepository.save(testUser);
    }

    @Test
    void should_returnTrue_when_userExistsByUsername() {
        boolean expected = userRepository.existsByUsername("josh.wood@me.com");
        assertThat(expected).isTrue();
    }

}