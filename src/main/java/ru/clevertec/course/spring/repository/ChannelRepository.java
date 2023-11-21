package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.course.spring.model.domain.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
