package ru.red_collar.dto;

import java.time.LocalDate;
import ru.red_collar.entity.UserRole;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    Integer id;

    String name;

    String email;

    LocalDate birthday;

    UserRole role;
}
