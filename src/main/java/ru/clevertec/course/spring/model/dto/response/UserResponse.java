package ru.clevertec.course.spring.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponse {
    private Long id;

    private String nickname;

    private String name;

    private String email;
}
