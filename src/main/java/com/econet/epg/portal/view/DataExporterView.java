package com.econet.epg.portal.view;

import com.econet.epg.portal.controller.PaymentBean;
import com.econet.epg.portal.model.Payment;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class DataExporterView implements Serializable {

    private List<Payment> payments;

    @Inject
    @ManagedProperty("#{paymentBean}")
    private PaymentBean service;
    @Inject
    @ManagedProperty("#{treeBean}")
    private TreeBean treeBean;

    @PostConstruct
    public void init() {
        if(treeBean.getPayments()==null) {
            payments = service.getPaymentList();
        }else{
            payments=treeBean.getPayments();
        }
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public PaymentBean getService() {
        return service;
    }

    public void setService(PaymentBean service) {
        this.service = service;
    }

    public TreeBean getTreeBean() {
        return treeBean;
    }

    public void setTreeBean(TreeBean treeBean) {
        this.treeBean = treeBean;
    }
}
