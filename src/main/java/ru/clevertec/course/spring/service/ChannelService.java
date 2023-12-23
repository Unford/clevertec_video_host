package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.Category;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.dto.request.ChannelCreateRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelFilterRequest;
import ru.clevertec.course.spring.model.dto.request.ChannelPatchRequest;
import ru.clevertec.course.spring.model.dto.response.ChannelFullResponse;
import ru.clevertec.course.spring.model.dto.response.ChannelShortResponse;
import ru.clevertec.course.spring.model.mapper.ChannelMapper;
import ru.clevertec.course.spring.repository.CategoryRepository;
import ru.clevertec.course.spring.repository.ChannelRepository;
import ru.clevertec.course.spring.repository.UserRepository;
import ru.clevertec.course.spring.repository.specification.ChannelSpecification;

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
    public ChannelFullResponse create(ChannelCreateRequest channelCreateRequest, MultipartFile file) {
        channelRepository.findChannelByTitle(channelCreateRequest.getTitle())
                .ifPresent(c -> {
                    throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                            .formatted(channelCreateRequest.getTitle()));
                });

        Channel channel = userRepository.findById(channelCreateRequest.getAuthor().getId())
                .map(u -> channelMapper.toEntity(channelCreateRequest).setAuthor(u))
                .map(u -> u.setCategory(prepareCategory(u.getCategory().getTitle())))
                .map(channelRepository::save)
                .map(ch -> saveImage(file, ch))
                .orElseThrow(() -> new ResourceNotFoundException("User associated with '%s' id is not found"
                        .formatted(channelCreateRequest.getAuthor().getId())));


        return channelMapper.toFullResponse(channel);
    }

    private Category prepareCategory(String category) {
        return category == null ? null :
                categoryRepository.findByTitle(category).orElse(new Category().setTitle(category));
    }

    @Transactional
    public ChannelFullResponse update(Long id, ChannelPatchRequest channelRequest, MultipartFile file) {
        channelRepository.findChannelByTitle(channelRequest.getTitle())
                .ifPresent(c -> {
                    throw new ResourceAlreadyExists("Channel title '%s' is already taken"
                            .formatted(channelRequest.getTitle()));
                });
        return channelRepository.findById(id)
                .map(c -> channelMapper.updateFromDto(channelRequest, c))
                .map(c -> channelRequest.getCategory() != null ?
                        c.setCategory(prepareCategory(channelRequest.getCategory())) : c)
                .map(channelRepository::save)
                .map(ch -> saveImage(file, ch))
                .map(channelMapper::toFullResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(channelRequest.getId())));
    }

    private Channel saveImage(MultipartFile multipartFile, Channel ch) {
        if (Objects.nonNull(multipartFile)) {
            String avatar = imageService.upload(multipartFile, ch.getId().toString());
            ch.setImage(avatar);
        }
        return ch;
    }

    public Page<ChannelShortResponse> findAllFiltered(ChannelFilterRequest cfr) {
        return channelRepository
                .findAll(new ChannelSpecification(cfr.getTitle(), cfr.getLanguage(), cfr.getCategory()), cfr.toPageable())
                .map(s -> channelMapper.toShortResponse(s, Hibernate.size(s.getSubscribers())));
    }

    public byte[] findChannelAvatar(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(id)));
        return imageService.getImage(channel.getImage());
    }

    public ChannelFullResponse findById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(id)));
        String encodedImage = imageService.getEncodedImage(channel.getImage());
        return channelMapper.toFullResponse(channel)
                .setImage(encodedImage)
                .setSubscriberCount(Hibernate.size(channel.getSubscribers()));
    }


}
