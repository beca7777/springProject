package com.users.service;

import com.users.dto.RequestDto;
import com.users.dto.SearchRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class FilterSpecificationService<T> {

    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDto,
                                                   RequestDto.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (SearchRequestDto requestDto : searchRequestDto) {
                switch (requestDto.getOperation()) {
                    case EQUAL:
                        predicates.add(buildEqualPredicate(root, criteriaBuilder, requestDto));
                        break;
                    case LIKE:
                        predicates.add(buildLikePredicate(root, criteriaBuilder, requestDto));
                        break;
                    case IN:
                        predicates.add(buildInPredicate(root, requestDto));
                        break;
                    case LESS_THAN:
                        predicates.add(buildLessThanPredicate(root, criteriaBuilder, requestDto));
                        break;
                    case GREATER_THAN:
                        predicates.add(buildGreaterThanPredicate(root, criteriaBuilder, requestDto));
                        break;
                    case BETWEEN:
                        predicates.add(buildBetweenPredicate(root, criteriaBuilder, requestDto));
                        break;
                    case JOIN:
                        predicates.add(buildJoinPredicate(root, criteriaBuilder, requestDto));
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected value " + requestDto.getOperation());
                }
            }
            return buildFinalPredicate(criteriaBuilder, predicates, globalOperator);
        };
    }

    private Predicate buildEqualPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        return criteriaBuilder.equal(root.get(requestDto.getColumn()), requestDto.getValue());
    }

    private Predicate buildLikePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        return criteriaBuilder.like(root.get(requestDto.getColumn()), "%" + requestDto.getValue() + "%");
    }

    private Predicate buildInPredicate(Root<T> root, SearchRequestDto requestDto) {
        String[] split = requestDto.getValue().split(",");
        return root.get(requestDto.getColumn()).in(Arrays.asList(split));
    }

    private Predicate buildLessThanPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        return criteriaBuilder.lessThan(root.get(requestDto.getColumn()), requestDto.getValue());
    }

    private Predicate buildGreaterThanPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        return criteriaBuilder.greaterThan(root.get(requestDto.getColumn()), requestDto.getValue());
    }

    private Predicate buildBetweenPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        String[] split = requestDto.getValue().split(",");
        return criteriaBuilder.between(root.get(requestDto.getColumn()), Long.parseLong(split[0]), Long.parseLong(split[1]));
    }

    private Predicate buildJoinPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, SearchRequestDto requestDto) {
        return criteriaBuilder.equal(root.join(requestDto.getJoinTable()).get(requestDto.getColumn()), requestDto.getValue());
    }

    private Predicate buildFinalPredicate(CriteriaBuilder criteriaBuilder, List<Predicate> predicates, RequestDto.GlobalOperator globalOperator) {
        if (globalOperator.equals(RequestDto.GlobalOperator.AND)) {
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        } else {
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        }
    }
}
