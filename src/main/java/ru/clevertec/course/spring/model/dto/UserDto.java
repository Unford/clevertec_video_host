package ru.clevertec.course.spring.model.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.clevertec.course.spring.model.validation.CreateValidation;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDto {

    private Long id;
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String nickname;
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String name;
    @Email
    @NotNull(groups = CreateValidation.class)
    @Size(max = 255)
    private String email;

}
