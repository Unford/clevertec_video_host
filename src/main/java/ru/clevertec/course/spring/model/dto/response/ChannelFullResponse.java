package ru.clevertec.course.spring.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.clevertec.course.spring.model.dto.UserDto;

import java.time.LocalDate;

@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelFullResponse extends ChannelShortResponse {
    private String description;
    private UserDto author;
    private LocalDate createDate;
}
