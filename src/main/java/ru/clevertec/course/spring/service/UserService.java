package ru.clevertec.course.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.course.spring.exception.ResourceAlreadyExists;
import ru.clevertec.course.spring.exception.ResourceNotFoundException;
import ru.clevertec.course.spring.model.domain.User;
import ru.clevertec.course.spring.model.dto.UserDto;
import ru.clevertec.course.spring.model.mapper.UserMapper;
import ru.clevertec.course.spring.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;


    public UserDto createUser(UserDto userDto) {
        checkUniqueNicknameAndEmail(userDto.getNickname(), userDto.getEmail());
        return mapper.toDto(userRepository.save(mapper.toEntity(userDto)));
    }

    private void checkUniqueNicknameAndEmail(String nickname, String email){
        userRepository.findUserByNickname(nickname).ifPresent(u -> {
            throw new ResourceAlreadyExists("User nickname \"%s\" is already taken".formatted(nickname));
        });
        userRepository.findUserByEmail(email).ifPresent(u -> {
            throw new ResourceAlreadyExists("User email \"%s\" is already taken\"".formatted(email));
        });
    }

    public UserDto update(UserDto userDto) {
        checkUniqueNicknameAndEmail(userDto.getNickname(), userDto.getEmail());
        return userRepository.findById(userDto.getId())
                .map(u -> mapper.updateFromDto(userDto, u))
                .map(userRepository::save)
                .map(mapper::toDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User associated with %s id is not found"
                                .formatted(userDto.getId())));
    }

    public List<UserDto> findAll() {
        return mapper.toDto(userRepository.findAll());
    }
}
