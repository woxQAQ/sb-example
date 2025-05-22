package com.woxqaq.im.router.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woxqaq.im.api.RouterAPI;
import com.woxqaq.im.api.models.ChatReq;
import com.woxqaq.im.api.models.IMServerRes;
import com.woxqaq.im.api.models.LoginReq;
import com.woxqaq.im.api.models.RegisterReq;
import com.woxqaq.im.api.models.RegisterRes;
import com.woxqaq.im.common.meta.MetaStore;
import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.res.BaseResponse;
import com.woxqaq.im.common.res.NullBody;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;

@Controller
public class RouterController implements RouterAPI {
    @Resource
    private MetaStore metaStore;

    @Operation(summary = "group chat")
    @RequestMapping(value = "groupChat", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public BaseResponse<NullBody> groupRoute(ChatReq req) {
        throw new UnsupportedOperationException();
    }

    @Operation(summary = "offline")
    @RequestMapping(value = "offLine", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public BaseResponse<NullBody> offLine(ChatReq groupReqVO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'offLine'");
    }

    @Operation(summary = "login")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<IMServerRes> login(LoginReq loginReqVO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Operation(summary = "register")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody()
    @Override
    public BaseResponse<RegisterRes> registerAccount(RegisterReq registerInfoReqVO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerAccount'");
    }

    @Override
    @Operation(summary = "online user")
    @RequestMapping(value = "onlineUser", method = RequestMethod.GET)
    @ResponseBody()
    public BaseResponse<Set<UserInfo>> onlineUser() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onlineUser'");
    }

}
