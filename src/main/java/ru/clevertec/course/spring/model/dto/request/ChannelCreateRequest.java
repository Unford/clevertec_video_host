package ru.clevertec.course.spring.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ChannelCreateRequest {


    @NotNull
    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    @NotNull
    private @Valid AuthorDto author;

    @Size(max = 255)
    private String category;

    @Size(max = 255)
    private String language;




    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorDto {
        @NotNull
        private Long id;

    }
}