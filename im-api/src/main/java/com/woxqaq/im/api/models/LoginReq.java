package com.woxqaq.im.api.models;

import com.woxqaq.im.common.req.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginReq extends BaseRequest {
    @Schema(description = "user id", example = "123456")
    private Long userId;
    @Schema(description = "user name", example = "woxqaq")
    private String userName;
}
