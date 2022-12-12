package com.yondu.university.project_rohan.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomPage<T> {
    @JsonProperty(index = 3)
    private List<T> data;

    @JsonProperty(index = 1)
    private Integer page;

    @JsonProperty(index = 2)
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
