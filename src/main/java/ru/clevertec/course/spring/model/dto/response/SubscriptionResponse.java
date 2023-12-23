package ru.clevertec.course.spring.model.dto.response;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

@Data
@Accessors(chain = true)
public class SubscriptionResponse {
    @Positive
    @NotNull
    private Long user;

    @Positive
    @NotNull
    private Long channel;
}
