package com.woxqaq.im.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.woxqaq.im.api.CoreApi;
import com.woxqaq.im.api.models.SendMessageRequest;
import com.woxqaq.im.api.models.SendMessageResponse;
import com.woxqaq.im.common.enums.StatusEnum;
import com.woxqaq.im.common.res.BaseResponse;
import com.woxqaq.im.core.server.ImServer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@RequestMapping("/")
public class ServerControllerImpl implements CoreApi {
    @Autowired
    private ImServer imServer;

    @Operation(summary = "push message to client")
    @RequestMapping(value = "sendMsg", method = RequestMethod.POST)
    @RequestBody
    public BaseResponse<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest req) {
        BaseResponse<SendMessageResponse> res = new BaseResponse<>();
        imServer.sendMessage(req);

        SendMessageResponse resp = new SendMessageResponse();
        resp.setMessage("OK");
        res.setCode(StatusEnum.SUCCESS.getCode());
        res.setMessage(StatusEnum.SUCCESS.getDesc());
        res.setData(resp);
        return res;
    }
}
