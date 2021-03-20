package com.app.domain.user;

import com.app.domain.user.characteristics.Role;
import com.app.domain.user.dto.RegisterUserDtoResponse;
import com.app.domain.user.dto.SecurityUserDto;
import com.app.infrastructure.repository.entity.UserDocument;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class User {
    String id;
    String login;
    String password;
    String name;
    String surname;
    Role role;
    LocalDateTime accountCreationDate;

    public boolean hasPasswordEqualsTo(String password) {
        System.out.println("DB PASSWORD==========================");
        System.out.println(this.password);
        System.out.println("=====================================");
        return this.password.equals(password);
    }


    public UserDocument toUserDocument() {
        return UserDocument.builder()
                .name(name)
                .surname(surname)
                .login(login)
                .password(password)
                .role(role)
                .accountCreationDate(accountCreationDate == null ? LocalDateTime.now() : accountCreationDate)
                .build();
    }

    public RegisterUserDtoResponse toRegisteredUserDtoResponse() {
        return RegisterUserDtoResponse
                .builder()
                .username(login)
                .registrationDateTimeSTamp(accountCreationDate)
                .build();
    }

    public User withActivatedStatus() {
        return User
                .builder()
                .name(name)
                .surname(surname)
                // ....
                // .enabled(true)
                // ....
                .build();
    }

    public SecurityUserDto toSecurityUserDto() {
        return SecurityUserDto
                .builder()
                .login(login)
                .roles(List.of(role.toString()))
                .build();
    }

}
