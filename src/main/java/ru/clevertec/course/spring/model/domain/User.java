package ru.clevertec.course.spring.model.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nickname;
    private String name;
    @Column(unique = true)
    private String email;

    @ManyToMany(mappedBy = "subscribers")
    @ToString.Exclude
    private Set<Channel> subscribedChannels;



}
