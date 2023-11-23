package ru.clevertec.course.spring.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.Category;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;
import ru.clevertec.course.spring.model.mapper.ChannelMapper;
import ru.clevertec.course.spring.repository.CategoryRepository;
import ru.clevertec.course.spring.repository.ChannelRepository;
import ru.clevertec.course.spring.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {
    private final ImageService imageService;

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    private final ChannelMapper channelMapper;

    @Transactional
    public ChannelFullResponse create(ChannelRequest channelRequest) {
        channelRepository.findChannelByTitle(channelRequest.getTitle())
                .ifPresent(c -> { throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                            .formatted(channelRequest.getTitle()));
                });

        Channel channel = userRepository.findById(channelRequest.getAuthor().getId())
                .map(u -> channelMapper.toEntity(channelRequest).setAuthor(u))
                .map(u -> u.setCategory(prepareCategory(u.getCategory().getTitle())))
                .map(channelRepository::save)
                .map(ch -> {
                    if (Objects.nonNull(channelRequest.getFile())) {
                        String avatar = imageService.upload(channelRequest.getFile(), ch.getId().toString());
                        ch.setImage(avatar);
                    }
                    return ch;
                })
                .orElseThrow(() -> new ResourceNotFoundException("User associated with '%s' id is not found"
                        .formatted(channelRequest.getAuthor().getId())));


        return channelMapper.toFullResponse(channel);
    }

    private Category prepareCategory(String category) {
        return category == null ? null :
                categoryRepository.findByTitle(category).orElse(new Category().setTitle(category));
    }

    @Transactional
    public ChannelFullResponse update(ChannelPatchRequest channelRequest) {
        channelRepository.findChannelByTitle(channelRequest.getTitle())
                .ifPresent(c -> { throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                        .formatted(channelRequest.getTitle()));
                });
        return channelRepository.findById(channelRequest.getId())
                .map(c -> channelMapper.updateFromDto(channelRequest, c))
                .map(c -> channelRequest.getCategory() != null ?
                        c.setCategory(prepareCategory(channelRequest.getCategory())) : c)
                .map(channelRepository::save)
                .map(ch -> {
                    if (Objects.nonNull(channelRequest.getFile())) {
                        String avatar = imageService.upload(channelRequest.getFile(), ch.getId().toString());
                        ch.setImage(avatar);
                    }
                    return ch;
                })
                .map(channelMapper::toFullResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(channelRequest.getId())));


    }

    public List<ChannelShortResponse> findAll() {
        return channelRepository.findAll().stream().map(channelMapper::toShortResponse).toList();
    }

}
