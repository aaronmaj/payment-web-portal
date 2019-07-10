package com.econet.epg.portal.controller;

import com.econet.epg.portal.model.Category;
import com.econet.epg.portal.model.Detail;
import com.econet.epg.portal.model.Payment;
import com.econet.epg.portal.rest.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Aaron MAJAMBO
 * email: aaron.majambo@econet-leo.com / aaronmajb@gmail.com
 */

@Named
@ConversationScoped
public class PaymentBean implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(PaymentBean.class);
    private Long id;
    private Payment payment;
    @Produces
    @Named
    private FacesContext facesContext;
    @Inject
    private Conversation conversation;
    private long count;


    private List<Payment> payments = getPaymentList();

    public List<Payment> getPaymentList() {
        List<Payment> paymentList = new ArrayList<Payment>();

        try {
            paymentList = RestClient.getPayments();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return paymentList;


    }

    public Payment getPaymentDetails(Long id) {
        return null;
    }

    public void init() {
        if (!facesContext.isPostback() && conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String finishConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
        return "index?faces-redirect=true";
    }

    public void retrieve() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            return;
        }

        if (this.conversation.isTransient()) {
            this.conversation.begin();
            //this.conversation.setTimeout(1800000L);
        }
        this.payment = findById(getId());
        if (payment != null) {
            payment.setDetails(this.getDetails(id));
        }
    }

    private List<Detail> getDetails(Long id) {

        List<Detail> details = new ArrayList<Detail>();
        try {
            details = RestClient.getPaymentDetails(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return details;
    }

    private Payment findById(Long id) {
        Payment payment = null;
        try {
          Optional<Payment>optPayment=payments.stream().filter(payment1 -> payment1.getId().equals(id)).findAny();
          if(optPayment.isPresent()){
              payment=optPayment.get();
          }else {
              payment = RestClient.getPaymentById(id);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payment;
    }

    public List<Category> loadCategories() {
        List<Category> categories = new ArrayList<Category>();
        try {
            categories = RestClient.getCategories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }


}



