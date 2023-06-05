package com.jumar.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jumar.user.dto.CreateUserDto;
import com.jumar.user.dto.UpdateUserDto;
import com.jumar.user.fixtures.UserFixtures;
import com.jumar.user.models.User;
import com.jumar.user.repository.UserRepository;
import com.jumar.user.services.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	private CreateUserDto testCreateUserDto;
	private User testUser;
	private UpdateUserDto testUpdateUserDto;

	@BeforeEach
	void setUp() {
		testUser = UserFixtures.generateValidUser();
		testCreateUserDto = UserFixtures.generateCreateUserDto();
		testUpdateUserDto = UserFixtures.generateUpdateUserDto();
	}

	@Test
	void contextLoads() {
	}

	@SneakyThrows
	@Test
	void should_returnCorrectResponseDTO_when_createUser() {
		mockMvc.perform(post("/api/user/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testCreateUserDto)))
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.response").exists())
				.andExpect(jsonPath("$.message").isEmpty())
				.andReturn();
	}

	@SneakyThrows
	@Test
	void should_containUser_in_database_when_createUser() {
		mockMvc.perform(post("/api/user/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testCreateUserDto)))
				.andExpect(status().isCreated());

		User result = userRepository.findById(1).orElseThrow();

		assertThat(result).usingRecursiveComparison().ignoringFields(
				"dateLastModified", "dateAdded").isEqualTo(testUser);
	}

	@SneakyThrows
	@Test
	void should_returnFalse_when_comparingUpdatedUser_to_OriginalUser() {
		mockMvc.perform(post("/api/user/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testCreateUserDto)))
				.andExpect(status().isCreated());

		User original = userRepository.findById(1).orElseThrow();

		mockMvc.perform(put("/api/user/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testUpdateUserDto)))
				.andExpect(status().isOk());

		User updatedUser = userRepository.findById(1).orElseThrow();

		assertThat(updatedUser).isNotEqualTo(original);
	}

	@SneakyThrows
	@Test
	void should_deleteUser_from_database_when_deleteUser() {
		mockMvc.perform(post("/api/user/create")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testCreateUserDto)))
				.andExpect(status().isCreated());

		mockMvc.perform(delete("/api/user/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testUpdateUserDto)))
				.andExpect(status().isOk());

		assertThat(userRepository.findById(1).get().isDeleted()).isTrue();
	}

}
