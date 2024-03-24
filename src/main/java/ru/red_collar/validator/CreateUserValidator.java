package ru.red_collar.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static lombok.AccessLevel.PRIVATE;
import ru.red_collar.dao.UserDao;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.entity.UserRole;
import lombok.NoArgsConstructor;
import ru.red_collar.util.LocalDateUtil;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    private final UserDao userDao = UserDao.getInstance();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    private static final Set<Character> LATIN_CHARS_ONLY =
        Stream.concat(
                Stream.iterate('a', ch -> ch != 'z' + 1, ch -> (char) (ch + 1)),
                Stream.iterate('A', ch -> ch != 'Z' + 1, ch -> (char) (ch + 1)))
            .collect(Collectors.toUnmodifiableSet());

    @Override
    public ValidationResult isValid(final CreateUserDto createUserDto) {
        final ValidationResult validationResult = new ValidationResult();
        if (!isContainLatinCharsOnly(createUserDto.getName())) {
            validationResult.add(Error.of("invalid.name", "Name must consist of Latin letters"));
        }
        if (userDao.findByName(createUserDto.getName()).isPresent()) {
            validationResult.add(Error.of("Invalid.name", "Name is already exists"));
        }
        if (LocalDateUtil.isValid(createUserDto.getBirthday())) {
            final LocalDate localDate = LocalDateUtil.format(createUserDto.getBirthday());
            if (ChronoUnit.YEARS.between(localDate, LocalDate.now()) < 18) {
                validationResult.add(Error.of("invalid.date", "Age is invalid"));
            }
        } else {
            validationResult.add(Error.of("invalid.date", "Date format is invalid"));
        }
        if (userDao.findByEmail(createUserDto.getEmail()).isPresent()) {
            validationResult.add(Error.of("invalid.email", "Email is already exists"));
        }
        if (UserRole.find(createUserDto.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }

    private boolean isContainLatinCharsOnly(final String name) {
        return name.chars()
            .mapToObj(c -> (char) c)
            .allMatch(LATIN_CHARS_ONLY::contains);
    }
}
