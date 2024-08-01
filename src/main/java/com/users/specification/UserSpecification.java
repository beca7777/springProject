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

        if (criteria.getPrimaryEmail() != null) {
            if (criteria.getPrimaryEmail().getEquals() != null) {
                predicates.add(criteriaBuilder.equal(root.get("primaryEmail"), criteria.getPrimaryEmail().getEquals()));
            }
            if (criteria.getPrimaryEmail().getContains() != null) {
                predicates.add(criteriaBuilder.like(root.get("primaryEmail"), "%" + criteria.getPrimaryEmail().getContains() + "%"));
            }
            if (criteria.getPrimaryEmail().getIn() != null && !criteria.getPrimaryEmail().getIn().isEmpty()) {
                predicates.add(root.get("primaryEmail").in(criteria.getPrimaryEmail().getIn()));
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

        if (criteria.getSecondaryEmail() != null) {
            if (criteria.getSecondaryEmail().getEquals() != null) {
                predicates.add(criteriaBuilder.equal(root.get("secondaryEmails"), criteria.getSecondaryEmail().getEquals()));
            }
            if (criteria.getSecondaryEmail().getContains() != null) {
                predicates.add(criteriaBuilder.like(root.get("secondaryEmails"), "%" + criteria.getSecondaryEmail().getContains() + "%"));
            }
            if (criteria.getSecondaryEmail().getIn() != null && !criteria.getSecondaryEmail().getIn().isEmpty()) {
                predicates.add(root.get("secondaryEmails").in(criteria.getSecondaryEmail().getIn()));
            }
        }

        if (criteria.getPhoneNumber() != null) {
            if (criteria.getPhoneNumber().getEquals() != null) {
                predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), criteria.getPhoneNumber().getEquals()));
            }
            if (criteria.getPhoneNumber().getContains() != null) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + criteria.getPhoneNumber().getContains() + "%"));
            }
            if (criteria.getPhoneNumber().getIn() != null && !criteria.getPhoneNumber().getIn().isEmpty()) {
                predicates.add(root.get("phoneNumber").in(criteria.getPhoneNumber().getIn()));
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

