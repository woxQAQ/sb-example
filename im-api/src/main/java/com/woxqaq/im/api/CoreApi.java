package com.woxqaq.im.api;

import com.woxqaq.im.api.models.SendMessageRequest;
import com.woxqaq.im.api.models.SendMessageResponse;
import com.woxqaq.im.common.res.BaseResponse;

public interface CoreApi {
    BaseResponse<SendMessageResponse> sendMessage(SendMessageRequest req);
}
