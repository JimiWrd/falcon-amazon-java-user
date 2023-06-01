package com.jumar.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String forenames;
    private String surname;
    @Column(name = "email_address")
    private String emailAddress;
    @NotNull(message = "Telephone must not be empty.")
    private String telephone;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "date_added")
    private LocalDateTime dateAdded;
    @Column(name = "date_last_modified")
    private LocalDateTime dateLastModified;
    @Column(name = "failed_login_attempts")
    private int failedLoginAttempts;
    @Column(name = "deleted")
    private boolean deleted;
}
