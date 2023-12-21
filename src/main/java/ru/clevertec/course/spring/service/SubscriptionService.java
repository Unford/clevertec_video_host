package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.course.spring.exception.BusinessLogicException;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.response.SubscriptionResponse;
import ru.clevertec.course.spring.repository.ChannelRepository;
import ru.clevertec.course.spring.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionService {
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Transactional
    public SubscriptionResponse subscribe(Long userId, Long channelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User associated with %s id is not found"
                        .formatted(userId)));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(userId)));

        if (channel.getAuthor().equals(user)) {
            throw new BusinessLogicException("Author can't be subscribed to the channel");
        }
        if (user.getSubscribedChannels().contains(channel)) {
            throw new ResourceAlreadyExists("User is already subscribed to the channel");
        }
        user.getSubscribedChannels().add(channel);
        return new SubscriptionResponse(userId, channelId);
    }

    @Transactional
    public void unsubscribe(Long userId, Long channelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User associated with %s id is not found"
                        .formatted(userId)));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ResourceNotFoundException("Channel associated with %s id is not found"
                        .formatted(channelId)));

        if (!user.getSubscribedChannels().contains(channel)) {
            throw new ResourceNotFoundException("User is not subscribed to the channel");
        }
        user.getSubscribedChannels().remove(channel);
    }


}
