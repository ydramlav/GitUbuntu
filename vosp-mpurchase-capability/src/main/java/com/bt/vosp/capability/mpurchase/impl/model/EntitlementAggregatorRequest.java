package com.bt.vosp.capability.mpurchase.impl.model;

import java.util.Map;

import com.bt.vosp.common.model.CommonRequestBean;

public class EntitlementAggregatorRequest extends CommonRequestBean{
    private String schema;
    private String correlationId;
    private String contentType;

    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
        this.schema = schema;
    }
    public String getCorrelationId() {
        return correlationId;
    }
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    @Override
    public void setQueryParams(Map<String, String> queryParams) {

        if (this.getSchema() != null && !this.getSchema().isEmpty()) {
            queryParams.put("schema", this.getSchema());
        }
        if (this.getCorrelationId() != null && !this.getCorrelationId().isEmpty()) {
            queryParams.put("correlationId", this.getCorrelationId());
        }
        this.queryParams = queryParams;
    }
    @Override
    public void setHeaders(Map<String, String> headers) {
        if (this.getContentType() != null && !this.getContentType().isEmpty()) {
            headers.put("Accept", this.getContentType());
            headers.put("Content-Type", this.getContentType());
        }
        this.headers = headers;

    }



}
