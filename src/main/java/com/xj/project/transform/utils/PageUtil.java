package com.xj.project.transform.utils;

/**
 * 分页工具类
 *
 * @author xiangjing
 * @date 2018/4/2
 * @company 天极云智
 */
public class PageUtil {

    private int pageNo=1;

    private int pageSize=10;

    private int start=0;

    private int end=10;

    private int total;

    private int totalPage;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
        this.start = (pageNo-1)*pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.start = (pageNo-1)*pageSize;
        this.end = this.pageSize;

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPage() {
        if( this.total%this.pageSize == 0){
            return this.total/this.pageSize;
        }else{
            return (this.total/this.pageSize)+1;
        }
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
