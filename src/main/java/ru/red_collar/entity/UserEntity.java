package ru.red_collar.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    private Integer id;

    private String name;

    private LocalDate birthday;

    private String email;

    private String password;

    private UserRole role;
}
