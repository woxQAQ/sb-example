package com.woxqaq.im.core.server;

import com.woxqaq.im.core.models.SendMessageRequest;

public interface ImServer {
    /*
     * @SendMessage
     * 
     * @param req
     */
    void sendMessage(SendMessageRequest req);
}
