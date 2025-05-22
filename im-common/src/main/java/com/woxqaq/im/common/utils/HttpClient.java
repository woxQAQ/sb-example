package com.woxqaq.im.common.utils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class HttpClient {
    private static MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

    public static Response post(OkHttpClient client, String param, String url) throws IOException {
        RequestBody body = RequestBody.create(mediaType, param);
        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response resp = client.newCall(req).execute();
        if (!resp.isSuccessful()) {
            throw new IOException("Unexpected code " + resp);
        }

        return resp;
    }

    public static Response get(OkHttpClient client, String url) throws IOException {
        Request req = new Request.Builder()
                .url(url)
                .get()
                .build();
        Response resp = client.newCall(req).execute();
        if (!resp.isSuccessful()) {
            throw new IOException("Unexpected code " + resp);
        }

        return resp;
    }
}
