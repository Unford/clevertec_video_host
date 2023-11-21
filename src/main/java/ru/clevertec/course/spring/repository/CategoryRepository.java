package ru.clevertec.course.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.course.spring.model.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
