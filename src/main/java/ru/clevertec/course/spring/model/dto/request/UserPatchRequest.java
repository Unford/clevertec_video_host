package ru.clevertec.course.spring.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserPatchRequest {
    @Size(max = 255)
    private String nickname;
    @Size(max = 255)
    private String name;
    @Email
    @Size(max = 255)
    private String email;
}
