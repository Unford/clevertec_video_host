package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.*;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.request.UserCreateRequest;
import ru.clevertec.course.spring.model.dto.request.UserPatchRequest;
import ru.clevertec.course.spring.model.dto.response.UserResponse;

@Mapper
public interface UserMapper {

    @Mapping(target = "nickname",  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateFromDto(Long id, UserPatchRequest dto, @MappingTarget User entity);

    UserResponse toDto(User user);

    User toEntity(UserCreateRequest userDto);
}
