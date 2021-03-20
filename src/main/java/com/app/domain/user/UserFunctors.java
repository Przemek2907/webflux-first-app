package com.app.domain.user;

import java.util.function.Function;

public interface UserFunctors {
    Function<User, String> toId = user -> user.id;
    Function<User, String> toPassword = user -> user.password;
}
