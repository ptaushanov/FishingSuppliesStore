package com.ptaushanov.shop.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableHelpers {
    public static final String DEFAULT_SORT = "id,asc";

    public static Pageable createPageable(int page, int size, String sortString) {
        Sort sort = createSort(sortString);
        return PageRequest.of(page, size, sort);
    }

    public static Sort createSort(String sortString) {
        String sortArray[] = sortString.split(",");
        if (sortArray.length < 2) {
            throw new IllegalArgumentException(
                    "Sort parameter must be in format: property,asc|desc"
            );
        }
        String property = sortArray[0];
        String direction = sortArray[1];
        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new IllegalArgumentException(
                    "Sort parameter must be in format: property,asc|desc"
            );
        }
        return Sort.by(Sort.Direction.fromString(direction), property);
    }
}
