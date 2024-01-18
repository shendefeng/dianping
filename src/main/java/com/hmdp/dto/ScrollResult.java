package com.hmdp.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScrollResult {
    // ? 通配符, 加入的时候随便加, 不考虑数据类型, 取的时候要手动转型
    private List<?> list;
    private Long minTime;
    private Integer offset;
}
