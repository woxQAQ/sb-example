package com.woxqaq.im.api;

import java.util.Set;

import com.woxqaq.im.api.models.ChatReq;
import com.woxqaq.im.api.models.IMServerRes;
import com.woxqaq.im.api.models.LoginReq;
import com.woxqaq.im.api.models.RegisterReq;
import com.woxqaq.im.api.models.RegisterRes;
import com.woxqaq.im.common.annotations.Request;
import com.woxqaq.im.common.pojo.UserInfo;
import com.woxqaq.im.common.res.BaseResponse;
import com.woxqaq.im.common.res.NullBody;

public interface RouterAPI {

    /**
     * group chat
     *
     * @param groupReqVO
     * @return
     * @throws Exception
     */
    BaseResponse<NullBody> groupRoute(ChatReq groupReqVO);

    /**
     * Point to point chat
     * 
     * @param p2pRequest
     * @return
     * @throws Exception
     */
    // BaseResponse<NullBody> p2pRoute(P2PReqVO p2pRequest);

    /**
     * Offline account
     *
     * @param groupReqVO
     * @return
     * @throws Exception
     */
    BaseResponse<NullBody> offLine(ChatReq groupReqVO);

    /**
     * Login account
     * 
     * @param loginReqVO
     * @return
     * @throws Exception
     */
    BaseResponse<IMServerRes> login(LoginReq loginReqVO) throws Exception;

    /**
     * Register account
     *
     * @param registerInfoReqVO
     * @return
     * @throws Exception
     */
    BaseResponse<RegisterRes> registerAccount(RegisterReq registerInfoReqVO) throws Exception;

    /**
     * Get all online users
     *
     * @return
     * @throws Exception
     */
    @Request(method = Request.GET)
    BaseResponse<Set<UserInfo>> onlineUser() throws Exception;

}
