package com.econet.epg.portal.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="channel_accounts")
public class Channel implements Serializable {
    @Id
    private Integer id;
    @Transient
    private Date transferDate;
    @Column(name = "channel_name")
    private String billerName;
    @Column(name = "channel_msisdn")
    private String billerAcount;
    @Column(name = "channel_code")
    private String billerCode;
    @Transient
    private Double openingBalance;
    @Transient
    private Double inAmount;
    @Transient
    private Integer inCount;
    @Transient
    private Double outAmount;
    @Transient
    private Integer outCount;
    @Transient
    private Double closingBalance;

    public String getBillerAcount() {
        return billerAcount;
    }

    public void setBillerAcount(String billerAcount) {
        this.billerAcount = billerAcount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public Double getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }
    public Integer getOutCount() {
        return outCount;
    }
    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }
}
