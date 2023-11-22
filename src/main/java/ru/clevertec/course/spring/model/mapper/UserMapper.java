package ru.clevertec.course.spring.model.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.UserDto;

@Mapper(config = DefaultMapperConfig.class)
public interface UserMapper extends Mappable<User, UserDto> {
}
