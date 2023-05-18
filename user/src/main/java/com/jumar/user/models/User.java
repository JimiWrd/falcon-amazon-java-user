package com.jumar.user.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String forenames;

    @Column(name = "last_name")
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

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
