package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.*;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;

import java.util.List;

@Mapper(config = DefaultMapperConfig.class)
public interface ChannelMapper extends Mappable<Channel, ChannelRequest> {
    @Mapping(target = "category", source = "category.title")
    @Mapping(target = "subscribersCounter",
            expression = "java(channel.getSubscribers() == null ? 0: channel.getSubscribers().size())")
    ChannelFullResponse toFullResponse(Channel channel);

    @Override
    @Mapping(target = "category", source = "category.title")
    ChannelRequest toDto(Channel entity);

    @Override
    @Mapping(target = "category.title", source = "category")
    Channel toEntity(ChannelRequest dto);

    @Mapping(target = "category", source = "category.title")
    @Mapping(target = "subscribersCounter",
            expression = "java(channel.getSubscribers() == null ? 0: channel.getSubscribers().size())")
    ChannelShortResponse toShortResponse(Channel channel);
    @Mapping(target = "category.title", source = "category", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Channel updateFromDto(ChannelPatchRequest channelRequest, @MappingTarget Channel c);
}
