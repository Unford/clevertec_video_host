package ru.clevertec.course.spring.model.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChannelShortResponse {
    private String title;
    private String image;
    private Integer subscribersCounter;
    private String category;
    private String language;
}
