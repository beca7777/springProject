package com.users.specification;

import com.users.criteria.UserCriteria;
import com.users.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    private final UserCriteria criteria;

    public UserSpecification(UserCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getEmail() != null) {
            if (criteria.getEmail().getEquals() != null) {
                predicates.add(criteriaBuilder.equal(root.get("email"), criteria.getEmail().getEquals()));
            }
            if (criteria.getEmail().getContains() != null) {
                predicates.add(criteriaBuilder.like(root.get("email"), "%" + criteria.getEmail().getContains() + "%"));
            }
            if (criteria.getEmail().getIn() != null && !criteria.getEmail().getIn().isEmpty()) {
                predicates.add(root.get("email").in(criteria.getEmail().getIn()));
            }
        }

        if (criteria.getDateOfBirth() != null) {
            if (criteria.getDateOfBirth().getEquals() != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateOfBirth"), criteria.getDateOfBirth().getEquals()));
            }
            if (criteria.getDateOfBirth().getGreaterThan() != null) {
                predicates.add(criteriaBuilder.greaterThan(root.get("dateOfBirth"), criteria.getDateOfBirth().getGreaterThan()));
            }
            if (criteria.getDateOfBirth().getLessThan() != null) {
                predicates.add(criteriaBuilder.lessThan(root.get("dateOfBirth"), criteria.getDateOfBirth().getLessThan()));
            }
            if (criteria.getDateOfBirth().getGreaterThanOrEqualTo() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), criteria.getDateOfBirth().getGreaterThanOrEqualTo()));
            }
            if (criteria.getDateOfBirth().getLessThanOrEqualTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dateOfBirth"), criteria.getDateOfBirth().getLessThanOrEqualTo()));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

