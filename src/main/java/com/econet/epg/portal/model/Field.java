package com.econet.epg.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Field implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    @Transient
    private String value;
    private String description;
    private List<Detail> payments=new ArrayList<>();

    public Field() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonIgnore
    public List<Detail> getPayments() {
        return payments;
    }
    @JsonProperty
    public void setPayments(List<Detail> payments) {
        this.payments = payments;
    }
}
