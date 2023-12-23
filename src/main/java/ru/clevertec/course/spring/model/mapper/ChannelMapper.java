package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.*;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.dto.request.ChannelCreateRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;

@Mapper
public interface ChannelMapper {
    @Mapping(target = "category", source = "category.title")
    @Mapping(target = "subscriberCount", ignore = true)
    ChannelFullResponse toFullResponse(Channel channel);



    @Mapping(target = "category.title", source = "category")
    Channel toEntity(ChannelCreateRequest dto);

    @Mapping(target = "category", source = "channel.category.title")
    ChannelShortResponse toShortResponse(Channel channel, long subscriberCount);
    @Mapping(target = "category.title", source = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Channel updateFromDto(ChannelPatchRequest channelRequest, @MappingTarget Channel c);
}
