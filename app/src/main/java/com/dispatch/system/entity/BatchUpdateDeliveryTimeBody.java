package com.dispatch.system.entity;

import java.util.List;

public class BatchUpdateDeliveryTimeBody extends BaseBean {

    private static final long serialVersionUID = 1449694376844027591L;

    /**
     * trackingNumbers : ["SF123456789"]
     * deliveryTimeCode : ceb8b47dfa024858a1918b869e9ad485
     * dateType : TODAY
     */

    private String deliveryTimeCode;
    private String dateType;
    private List<String> trackingNumbers;

    public String getDeliveryTimeCode() {
        return deliveryTimeCode;
    }

    public void setDeliveryTimeCode(String deliveryTimeCode) {
        this.deliveryTimeCode = deliveryTimeCode;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public List<String> getTrackingNumbers() {
        return trackingNumbers;
    }

    public void setTrackingNumbers(List<String> trackingNumbers) {
        this.trackingNumbers = trackingNumbers;
    }
}
