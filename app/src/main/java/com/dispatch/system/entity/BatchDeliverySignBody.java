package com.dispatch.system.entity;

import java.util.List;

public class BatchDeliverySignBody extends BaseBean {

    private static final long serialVersionUID = 363644850764802169L;


    /**
     * trackingNumbers : ["SF11234124"]
     * signedFileCode : ceb8b47dfa024858a1918b869e9ad485
     */

    private String signedFileCode;
    private List<String> trackingNumbers;

    public String getSignedFileCode() {
        return signedFileCode;
    }

    public void setSignedFileCode(String signedFileCode) {
        this.signedFileCode = signedFileCode;
    }

    public List<String> getTrackingNumbers() {
        return trackingNumbers;
    }

    public void setTrackingNumbers(List<String> trackingNumbers) {
        this.trackingNumbers = trackingNumbers;
    }
}
