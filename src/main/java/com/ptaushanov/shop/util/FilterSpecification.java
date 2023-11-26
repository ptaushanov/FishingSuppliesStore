package com.ptaushanov.shop.util;

import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.jpa.domain.Specification;

public class FilterSpecification {
    private static final Logger logger = LoggerFactory.getLogger(FilterSpecification.class);
    private static final ConversionService conversionService = new DefaultConversionService();

    public static <T> Specification<T> filterQuery(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.isEmpty()) return criteriaBuilder.conjunction();
            String[] filterParams = filter.split(",");
            if (filterParams.length < 2) return criteriaBuilder.conjunction();

            String filterProperty = filterParams[0];
            String filterValue = filterParams[1];

            EntityType<T> entityType = root.getModel();
            SingularAttribute<? super T, ?> singularAttribute = entityType.getSingularAttribute(
                    filterProperty);

            Class<?> propertyType = singularAttribute.getJavaType();
            Object convertedValue = convertToPropertyType(filterValue, propertyType);

            return criteriaBuilder.equal(root.get(filterProperty), convertedValue);
        };
    }

    private static Object convertToPropertyType(String filterValue, Class<?> propertyType) {
        if (propertyType == String.class) return filterValue;
        try {
            return conversionService.convert(filterValue, propertyType);
        } catch (Exception e) {
            logger.error(
                    "Error converting value '{}' to type '{}'", filterValue,
                    propertyType.getSimpleName(), e
            );
            return null;
        }
    }
}