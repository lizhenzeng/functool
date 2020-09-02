package com.tiger.functool.base;

import lombok.Data;

@Data
public class BaseSuperDTO {
    private Integer pageSize = 10;//分页大小
    private Integer pageIndex = 0;//分页开始
}
