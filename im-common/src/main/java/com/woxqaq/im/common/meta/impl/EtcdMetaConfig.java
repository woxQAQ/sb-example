package com.woxqaq.im.common.meta.impl;

import org.apache.curator.RetryPolicy;

import com.woxqaq.im.common.meta.MetaConfig;

public class EtcdMetaConfig extends MetaConfig<RetryPolicy> {
    EtcdMetaConfig(String metaServiceUri, int timeoutMs, RetryPolicy retryPolicy) {
        super(metaServiceUri, timeoutMs, retryPolicy);
    }
}
