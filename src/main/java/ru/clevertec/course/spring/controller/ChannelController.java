package ru.clevertec.course.spring.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;
import ru.clevertec.course.spring.model.validation.CreateValidation;
import ru.clevertec.course.spring.service.ChannelService;

import java.util.List;

@RestController
@RequestMapping("api/v1/channels")
@RequiredArgsConstructor
@Validated
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ChannelFullResponse createChannel(@RequestPart("channel")
                                        @Validated({CreateValidation.class, Default.class})
                                        ChannelRequest channelRequest,
                                             @RequestPart(value = "file", required = false) MultipartFile file) {
        channelRequest.setFile(file);
        return channelService.create(channelRequest);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ChannelFullResponse createChannelWithoutImage(@RequestBody
                                        @Validated({CreateValidation.class, Default.class})
                                        ChannelRequest channelRequest) {
        return channelService.create(channelRequest);

    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public ChannelFullResponse updateChannel(@PathVariable("id") @Positive Long id,
                                             @RequestPart("channel") @Validated ChannelPatchRequest channelRequest,
                                             @RequestPart(value = "file", required = false) MultipartFile file) {
        channelRequest.setFile(file);
        channelRequest.setId(id);
        return channelService.update(channelRequest);
    }

    @PatchMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public ChannelFullResponse updateChannel(@PathVariable("id") @Positive Long id,
                                             @RequestBody @Validated ChannelPatchRequest channelRequest) {
        channelRequest.setId(id);
        return channelService.update(channelRequest);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ChannelShortResponse> findAll() {
        return channelService.findAll();
    }
}
