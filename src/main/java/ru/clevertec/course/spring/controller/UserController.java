package ru.clevertec.course.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.spring.model.dto.request.UserCreateRequest;
import ru.clevertec.course.spring.model.dto.request.UserPatchRequest;
import ru.clevertec.course.spring.model.dto.response.UserResponse;
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
    public UserResponse createUser(@RequestBody @Valid UserCreateRequest userDto) {

        return userService.create(userDto);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponse updateUser(@PathVariable("id") @Positive Long id,
                              @RequestBody @Valid UserPatchRequest userDto) {
        return userService.update(id,userDto);
    }

    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}/subscriptions")
    public List<String> getUserSubscriptionsNames(@PathVariable("id") @Positive Long id) {
        return userService.findAllSubscriptionsNamesById(id);
    }


}
