package com.app.infrastructure.repository.entity;

import com.app.domain.user.User;
import com.app.domain.user.characteristics.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Document(collection = "users")
public class UserDocument {

    @Id
    String id;

    String login;
    String password;
    String name;
    String surname;
    Role role;
    LocalDateTime accountCreationDate;


    public User toUser() {
        return User.builder()
                .id(id)
                .login(login)
                .password(password)
                .name(name)
                .surname(surname)
                .role(role)
                .accountCreationDate(accountCreationDate)
                .build();
    }

}
