package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.*;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.UserDto;

@Mapper
public interface UserMapper extends Mappable<User, UserDto> {

    @Mapping(target = "nickname",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateFromDto(UserDto dto, @MappingTarget User entity);
}
