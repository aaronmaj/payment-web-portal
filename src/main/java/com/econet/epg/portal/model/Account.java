package com.econet.epg.portal.model;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String enName;
    private String rnName;
    private SubCategory subCategory;
    private List<Payment> payments;

    public Account() {
    }

    /*@JsonCreator
    public Account(@JsonProperty(value = "accountNo", required = true) String accountNo) {
        this.accountNo = accountNo;
    }

    @JsonCreator
    public Account(@JsonProperty(value = "accountNo", required = true) String accountNo, @JsonProperty("name") String name) {
        this.accountNo = accountNo;
        this.name = name;
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRnName() {
        return rnName;
    }

    public void setRnName(String rnName) {
        this.rnName = rnName;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
