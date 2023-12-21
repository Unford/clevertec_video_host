package ru.clevertec.course.spring.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.course.spring.model.dto.response.SubscriptionResponse;
import ru.clevertec.course.spring.service.SubscriptionService;

@RestController
@RequestMapping("api/v1/users/{userId}/subscriptions")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {
    private final SubscriptionService service;

    @PostMapping("/{channelId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SubscriptionResponse subscribeUserToChannel(@PathVariable("userId") @Positive Long userId,
                                                       @PathVariable("channelId") @Positive Long channelId) {
        return service.subscribe(userId, channelId);
    }

    @DeleteMapping("/{channelId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unsubscribeUserFromChannel(@PathVariable("userId") @Positive Long userId,
                                           @PathVariable("channelId") @Positive Long channelId) {
        service.unsubscribe(userId, channelId);
    }
}
