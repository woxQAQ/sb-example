package com.woxqaq.im.api.models;

import com.woxqaq.im.common.req.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterReq extends BaseRequest {
    @NotNull(message = "username cannot be empty")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "username", example = "woxqaq")
    private String userName;
}
