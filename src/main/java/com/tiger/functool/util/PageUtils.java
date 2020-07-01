package com.tiger.functool.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageUtils<T> implements Serializable {

    private Integer totalNum;//总条数
    private Integer totalPage;//总页数
    private Integer pageSize = 15;//每页条数
    private Integer pageIndex = 1;//当前页码
    private Object data;


    public List<T> getData(){
        return (List<T>) data;
    }

    public static PageUtils initPage(Integer totalNum, Integer pageSize, Integer pageIndex, Object data) {
        PageUtils page = new PageUtils();
        page.setTotalNum(totalNum);
        Integer totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        page.setTotalPage(totalPage);
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setData(data);
        return page;
    }


}