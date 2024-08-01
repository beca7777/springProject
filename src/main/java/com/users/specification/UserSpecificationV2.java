package com.users.specification;

import com.users.criteria.UserCriteriaEasy;
import com.users.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Objects;

public class UserSpecificationV2 {

    public static Specification<User> createSpecification(UserCriteriaEasy userCriteria) {
        Specification<User> specification = Specification.where(null);

        if (Objects.nonNull(userCriteria)) {
            if (Objects.nonNull(userCriteria.getPrimaryEmail()) && StringUtils.hasText(userCriteria.getPrimaryEmail())) {
                specification = specification.and(hasPrimaryEmail(userCriteria.getPrimaryEmail()));
            }
            if (Objects.nonNull(userCriteria.getDateOfBirth())) {
                specification = specification.and(hasDateOfBirth(userCriteria.getDateOfBirth()));
            }
            if(Objects.nonNull(userCriteria.getPhoneNumber()) && StringUtils.hasText(userCriteria.getPhoneNumber())){
                specification = specification.and(hasPhoneNumber(userCriteria.getPhoneNumber()));
            }
            if(Objects.nonNull(userCriteria.getSecondaryEmail()) && StringUtils.hasText((userCriteria.getSecondaryEmail()))) {
                specification = specification.and(hasSecondaryEmail(userCriteria.getSecondaryEmail()));
            }
        }

        return specification;
    }

    private static Specification<User> hasDateOfBirth(Instant dateOfBirth) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("dateOfBirth"), dateOfBirth);
    }

    private static Specification<User> hasPrimaryEmail(String primaryEmail) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("primaryEmail"), primaryEmail);
    }

    private static Specification<User> hasPhoneNumber(String phoneNumber){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber);
    }

    private static Specification<User> hasSecondaryEmail(String secondaryEmail){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("secondaryEmails"), "%" + secondaryEmail + "%");
    }

}