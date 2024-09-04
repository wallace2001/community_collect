package com.collect.community_collect.request;

import com.collect.community_collect.domain.Resource;

import java.util.List;

public class ExchangeRequest {

    private String fromCenterId;
    private String toCenterId;
    private List<Resource> resourcesSent;
    private List<Resource> resourcesReceived;

    // Getters and Setters

    public String getFromCenterId() {
        return fromCenterId;
    }

    public void setFromCenterId(String fromCenterId) {
        this.fromCenterId = fromCenterId;
    }

    public String getToCenterId() {
        return toCenterId;
    }

    public void setToCenterId(String toCenterId) {
        this.toCenterId = toCenterId;
    }

    public List<Resource> getResourcesSent() {
        return resourcesSent;
    }

    public void setResourcesSent(List<Resource> resourcesSent) {
        this.resourcesSent = resourcesSent;
    }

    public List<Resource> getResourcesReceived() {
        return resourcesReceived;
    }

    public void setResourcesReceived(List<Resource> resourcesReceived) {
        this.resourcesReceived = resourcesReceived;
    }
}
