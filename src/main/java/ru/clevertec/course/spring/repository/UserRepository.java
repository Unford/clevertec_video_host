package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
