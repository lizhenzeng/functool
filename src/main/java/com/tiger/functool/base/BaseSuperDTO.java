package com.tiger.functool.base;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class BaseSuperDTO {
    @NotEmpty(message = "pageSize不能为空!")
    private Integer pageSize = 10;//分页大小
    @NotEmpty(message = "pageIndex不能为空!")
    private Integer pageIndex = 0;//分页开始
}
