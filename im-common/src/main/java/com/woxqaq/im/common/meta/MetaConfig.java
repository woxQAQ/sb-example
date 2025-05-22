package com.woxqaq.im.common.meta;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MetaConfig<RETRY> {
    public MetaConfig(String metaServiceUri, int timeoutMs, RETRY retryPolicy) {
        this.metaServiceUri = metaServiceUri;
        this.timeoutMs = timeoutMs;
        this.retryPolicy = retryPolicy;
    }
    private String metaServiceUri;
    private int timeoutMs;
    private RETRY retryPolicy;

}
