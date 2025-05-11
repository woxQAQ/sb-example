package com.woxqaq.im.common.req;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseRequest {

    @Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "request id", example = "123456")
    private String reqId;

    @Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "timestamp", example = "0")
    private long timestamp;

    public BaseRequest() {
        setTimestamp(System.currentTimeMillis() / 1000);
    }
}
