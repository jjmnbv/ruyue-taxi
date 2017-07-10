package com.szyciov.supervision.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityInfoList<T> {
    private List<T> items;
}
