package com.econet.epg.portal.model;

import java.io.Serializable;

public class DetailKey implements Serializable{

    private static final long serialVersionUID = 1L;
    private Long paymentId;
    private Long fieldId;

    public DetailKey() {
    }

    public DetailKey(Long paymentId, Long fieldId) {
        this.paymentId = paymentId;
        this.fieldId = fieldId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetailKey)) return false;

        DetailKey detailKey = (DetailKey) o;

        if (!getPaymentId().equals(detailKey.getPaymentId())) return false;
        return getFieldId().equals(detailKey.getFieldId());
    }

    @Override
    public int hashCode() {
        int result = getPaymentId().hashCode();
        result = 31 * result + getFieldId().hashCode();
        return result;
    }
}
