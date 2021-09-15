package com.dispatch.system.entity;

import java.util.List;

public class BatchDeliveryExceptionBody extends BaseBean {

    private static final long serialVersionUID = -2542661118157599046L;

    /**
     * trackingNumbers : ["SF123456789"]
     * exceptionTemplateCode : ceb8b47dfa024858a1918b869e9ad485
     */

    private String exceptionTemplateCode;
    private List<String> trackingNumbers;

    public String getExceptionTemplateCode() {
        return exceptionTemplateCode;
    }

    public void setExceptionTemplateCode(String exceptionTemplateCode) {
        this.exceptionTemplateCode = exceptionTemplateCode;
    }

    public List<String> getTrackingNumbers() {
        return trackingNumbers;
    }

    public void setTrackingNumbers(List<String> trackingNumbers) {
        this.trackingNumbers = trackingNumbers;
    }
}
