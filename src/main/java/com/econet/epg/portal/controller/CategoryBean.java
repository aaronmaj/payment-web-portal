package com.econet.epg.portal.controller;

import com.econet.epg.portal.model.Category;
import com.econet.epg.portal.rest.RestClient;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class CategoryBean implements Serializable {

    private Long id;
    private Category category;
    private String categoryName;
    private List<Category> categories;

    @PostConstruct
    public void init() {
        categories = RestClient.getCategories();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
