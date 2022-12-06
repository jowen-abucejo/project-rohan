package com.yondu.university.project_rohan.dto;

import java.util.List;

public class CustomPage<T> {
    private List<T> data;
    private Integer page;
    private Integer size;

    /**
     * @param size
     */
    public CustomPage(List<T> data, Integer page, Integer size) {
        this.data = data;
        this.page = page;
        this.size = size;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return this.page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

}
