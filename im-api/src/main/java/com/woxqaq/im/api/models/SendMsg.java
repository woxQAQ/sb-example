package com.woxqaq.im.api.models;

import com.woxqaq.im.common.req.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMsg extends BaseRequest {
    @NotNull(message = "msg can not be null")
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "message", example = "hello world")
    private String msg;

    @NotNull(message = "id can not be null")
    @Schema(requiredMode = RequiredMode.NOT_REQUIRED, description = "user id", example = "123456")
    private long id;

}
