package ru.clevertec.course.spring.repository.specification;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.course.spring.model.domain.Category_;
import ru.clevertec.course.spring.model.domain.Channel;
import ru.clevertec.course.spring.model.domain.Channel_;

@AllArgsConstructor
public class ChannelSpecification implements Specification<Channel> {
    private static final String PERCENT_SIGN = "%";
    private final String title;
    private final String language;
    private final String category;

    @Override
    public Predicate toPredicate(Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate titlePredicate = title != null ? cb.like(cb.lower(root.get(Channel_.TITLE)),
                PERCENT_SIGN + title.toLowerCase() + PERCENT_SIGN) : cb.conjunction();

        Predicate languagePredicate = language != null ? cb.like(cb.lower(root.get(Channel_.LANGUAGE)),
                PERCENT_SIGN + language.toLowerCase() + PERCENT_SIGN) : cb.conjunction();

        Predicate categoryPredicate = category != null ? cb.like(cb.lower(root.join(Channel_.CATEGORY, JoinType.LEFT)
                .get(Category_.TITLE)), PERCENT_SIGN + category.toLowerCase() + PERCENT_SIGN) : cb.conjunction();

        return cb.and(titlePredicate, languagePredicate, categoryPredicate);
    }
}
