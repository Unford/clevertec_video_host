package ru.clevertec.course.spring.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserCreateRequest {
    @NotNull
    @Size(max = 255)
    private String nickname;
    @NotNull
    @Size(max = 255)
    private String name;
    @Email
    @NotNull
    @Size(max = 255)
    private String email;
}
