package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.projection.ChannelTitleOnly;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long>, JpaSpecificationExecutor<Channel> {
    Optional<Channel> findChannelByTitle(String title);



    List<ChannelTitleOnly> findBySubscribersId(Long id);


}
