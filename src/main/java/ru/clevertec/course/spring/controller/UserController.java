package ru.clevertec.course.spring.controller;

import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.UserDto;
import ru.clevertec.course.spring.model.mapper.UserMapper;
import ru.clevertec.course.spring.model.validation.CreateValidation;
import ru.clevertec.course.spring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserDto createUser(@RequestBody @Validated({CreateValidation.class, Default.class})
                              UserDto userDto) {
        userDto.setId(null);
        return userService.createUser(userDto);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto updateUser(@PathVariable("id") @Positive Long id,
                              @RequestBody @Validated UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<UserDto> getUsers() {
        return userService.findAll();
    }
}
