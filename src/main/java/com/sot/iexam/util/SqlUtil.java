package com.sot.iexam.util;

import org.springframework.data.domain.Sort;

/**
 * @author Kimbobo
 */
public class SqlUtil {
    public static Sort getSort(String direction, String orderBy) {
        String asc = "asc";
        String desc = "desc";
        Sort sort;
        if (asc.equals(direction)) {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        } else if (desc.equals(direction)) {
            sort = new Sort(Sort.Direction.DESC, orderBy);
        } else {
            sort = new Sort(Sort.Direction.ASC, orderBy);
        }
        return sort;
    }
}
    