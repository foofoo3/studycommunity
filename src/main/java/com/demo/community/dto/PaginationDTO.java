package com.demo.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * @author foofoo3
 */
@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage =totalPage;
        this.page = page;
//        如果总页数为0 则总页码数和页数为1
        if (totalPage == 0){
            totalPage = 1;
            page = 1;
            this.page = page;
            pages.add(page);
        }else {
            //        计算页码列表
            pages.add(page);
            for (int i = 1; i <= 3; i++) {
                if (page - i > 0){
                    pages.add(0,page - i);
                }
                if (page + i <= totalPage){
                    pages.add(page + i);
                }
            }
        }


        //是否显示上一页
        if (page == 1){
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        //是否显示下一页
        if (page.equals(totalPage)){
            showNext = false;
        }else {
            showNext = true;
        }
        //是否显示最前页
        if (pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage = true;
        }
        //是否显示最后页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
