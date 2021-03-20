package com.app.domain.user.dto;

import com.app.domain.user.User;
import com.app.domain.user.characteristics.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterUserDto {

    private String name;
    private String surname;
    private String login;
    private String password;
    private String passwordConfirmation;
    private String role;

    public User toUser() {
        return User.builder()
                .name(name)
                .surname(surname)
                .login(login)
                .password(password)
                .role(generateRole())
                .build();
    }

    private Role generateRole() {

        if (role != null && (role.equals("ROLE_ADMIN") || role.equals("ADMIN"))) {
            return Role.ROLE_ADMIN;
        }

        return Role.ROLE_USER;
    }
}
