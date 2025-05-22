package com.woxqaq.im.api.models;

import java.util.List;

import com.woxqaq.im.common.req.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatReq extends BaseRequest {
    @NotNull(message = "userId不能为空")
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "用户id", example = "123456")
    private Long userId;

    @NotNull(message = "msg不能为空")
    @Schema(requiredMode = RequiredMode.REQUIRED, description = "消息", example = "hello")
    private String msg;

    private List<String> batchMsg;
}
