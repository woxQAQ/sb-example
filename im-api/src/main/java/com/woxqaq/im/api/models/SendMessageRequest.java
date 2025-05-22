package com.woxqaq.im.api.models;

import java.util.List;
import java.util.Map;

import com.woxqaq.im.common.req.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SendMessageRequest extends BaseRequest {

    @NotNull(message = "msg cannot be empty")
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "message", example = "hello")
    private String msg;


    private List<String> batchMsg;

    @NotNull(message = "userId cannot be empty")
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "user id", example = "11223")
    private Long userId;

    private Map<String, String> options;

    public SendMessageRequest(String message, Long userId) {
        this.msg = message;
        this.userId = userId;
    }

    public boolean isBatch() {
        return batchMsg != null && batchMsg.size() != 0;
    }
}
