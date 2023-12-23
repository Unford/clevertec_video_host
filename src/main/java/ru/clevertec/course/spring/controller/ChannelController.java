package ru.clevertec.course.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.model.dto.request.ChannelCreateRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelFilterRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;
import ru.clevertec.course.spring.service.ChannelService;

@RestController
@RequestMapping("api/v1/channels")
@RequiredArgsConstructor
@Validated
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ChannelFullResponse createChannel(@RequestPart("channel")
                                             @Valid ChannelCreateRequest channelCreateRequest,
                                             @RequestPart(value = "file", required = false) MultipartFile file) {
        return channelService.create(channelCreateRequest, file);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping
    public ChannelFullResponse createChannelWithoutImage(@RequestBody
                                                         @Valid
                                                         ChannelCreateRequest channelCreateRequest) {
        return channelService.create(channelCreateRequest, null);

    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ChannelFullResponse updateChannel(@PathVariable("id") @Positive Long id,
                                             @RequestPart("channel") @Valid ChannelPatchRequest channelRequest,
                                             @RequestPart(value = "file", required = false) MultipartFile file) {
        return channelService.update(id, channelRequest, file);
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ChannelFullResponse updateChannel(@PathVariable("id") @Positive Long id,
                                             @RequestBody @Valid ChannelPatchRequest channelRequest) {
        return channelService.update(id, channelRequest, null);
    }

    @GetMapping
    public Page<ChannelShortResponse> findAll(@Valid ChannelFilterRequest channelFilterRequest) {
        return channelService.findAllFiltered(channelFilterRequest);
    }

    @GetMapping(value = "/{id}/avatar")
    @ResponseStatus(value = HttpStatus.OK)
    public byte[] getChannelAvatar(@PathVariable("id") @Positive Long id) {
        return channelService.findChannelAvatar(id);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ChannelFullResponse getChannelById(@PathVariable("id") @Positive Long id) {
        return channelService.findById(id);
    }
}
