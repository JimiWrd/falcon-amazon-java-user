package com.jumar.user.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordUtilsTest {

    @Test
    void should_returnTrue_when_comparingTwoHashesOfSamePassword() throws Exception{
        String password = "passwordTest";

        String firstPass = PasswordUtils.hashPassword(password);
        String secondPass = PasswordUtils.hashPassword(password);

        assertThat(firstPass).isEqualTo(secondPass);
    }

    @Test
    void should_returnTrue_when_comparingTwoHashesOfDifferentPassword() throws Exception{
        String passwordOne = PasswordUtils.hashPassword("passwordonetest");
        String passwordTwo = PasswordUtils.hashPassword("passwordtwotest");

        assertThat(passwordOne).isNotEqualTo(passwordTwo);
    }



}