package ru.clevertec.course.spring.model.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String nickname;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(mappedBy = "subscribers")
    @ToString.Exclude
    private Set<Channel> subscribedChannels;



}
