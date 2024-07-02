package com.users.specification;

import com.users.criteria.UserCriteria;
import com.users.criteria.UserCriteriaEasy;
import com.users.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecificationV2 {

    public static Specification<User> createSpecification(UserCriteriaEasy userCriteria) {
        Specification<User> specification = Specification.where(null);

        if (Objects.nonNull(userCriteria)) {
            if (Objects.nonNull(userCriteria.getEmail()) && StringUtils.hasText(userCriteria.getEmail())) {
                specification = specification.and(hasName(userCriteria.getEmail()));
            }
            if (Objects.nonNull(userCriteria.getDateOfBirth())) {
                specification = specification.and(hasDateOfBirth(userCriteria.getDateOfBirth()));
            }
        }

        return specification;
    }

    private static Specification<User> hasDateOfBirth(Instant dateOfBirth) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth);
    }

    private static Specification<User> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), name);
    }
}

