package com.example.demo.specification;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReactFilterSpecification {
    public static <T> Specification<T> parseSpecificationJson(String rawFilter) {
        return ((root, query, criteriaBuilder) -> {
            if (rawFilter == null ||  rawFilter.trim().isEmpty() || rawFilter.equals("{}")) {
                return criteriaBuilder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();

            try {
                Map<String, ?> filters = new ObjectMapper().readValue(rawFilter, Map.class);
                for (Map.Entry<String, ?> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null) continue;
                    if (key.endsWith("_id")) {
                        String relatedObject = key.substring(0, key.length() - 3);
                        predicates.add(criteriaBuilder.equal(root.get(relatedObject).get("id"), value));
                    }
                    else  if (key instanceof String) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(key)), "%" + value.toString().toLowerCase() + "%"));
                    }
                    else {
                        predicates.add(criteriaBuilder.equal(root.get(key), value));
                    }
                }
            criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            } catch (Exception e) {
                return criteriaBuilder.conjunction();
            }
            return null;
        });
    }
}
