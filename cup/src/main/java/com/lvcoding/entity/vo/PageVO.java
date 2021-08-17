package com.lvcoding.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author wuyanshen
 * @discription 分页实体
 * @date 2021-08-13 下午4:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

    private long current = 1;
    private long size = 10;
    private long total = 0;
    private List<T> records = Collections.emptyList();
}
