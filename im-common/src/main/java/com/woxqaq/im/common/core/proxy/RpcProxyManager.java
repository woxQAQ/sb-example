package com.woxqaq.im.common.core.proxy;

import static com.woxqaq.im.common.enums.StatusEnum.VALIDATION_FAIL;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woxqaq.im.common.annotations.DynamicUrl;
import com.woxqaq.im.common.annotations.Request;
import com.woxqaq.im.common.exception.IMException;
import com.woxqaq.im.common.utils.HttpClient;
import com.woxqaq.im.common.utils.StringUtil;

import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Slf4j
public class RpcProxyManager<T> {
    private Class<T> clazz;

    private OkHttpClient okHttpClient;

    private String url;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private RpcProxyManager(Class<T> clazz, OkHttpClient okHttpClient, String url) {
        this.clazz = clazz;
        this.okHttpClient = okHttpClient;
        this.url = url;
    }

    private RpcProxyManager() {
    }

    private RpcProxyManager(Class<T> clazz, OkHttpClient okHttpClient) {
        this(clazz, okHttpClient, "");
    }

    public static <T> T create(Class<T> clazz, String url, OkHttpClient okHttpClient) {
        return new RpcProxyManager<T>(clazz, okHttpClient, url).getInstance();
    }

    public static <T> T create(Class<T> clazz, OkHttpClient okHttpClient) {
        return new RpcProxyManager<T>(clazz, okHttpClient).getInstance();
    }

    @SuppressWarnings("unchecked")
    public T getInstance() {
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { clazz },
                new ProxyInvocateHandler());
    }

    private class ProxyInvocateHandler implements InvocationHandler {
        private Type getGenericType(Method method) {
            Type res = method.getGenericReturnType();
            if (res instanceof ParameterizedType parameterizedType) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type type : actualTypeArguments) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Response resp = null;
            String serverUrl = url + "/" + method.getName();
            Request reqAnnotation = method.getAnnotation(Request.class);

            if (reqAnnotation != null && StringUtil.isNotEmpty(reqAnnotation.url())) {
                serverUrl = url + "/" + reqAnnotation.url();
            }

            URI serveUri = new URI(serverUrl);
            serverUrl = serveUri.normalize().toString();

            Object param = null;
            Class<?> paramType = null;

            for (int i = 0; i < method.getParameterAnnotations().length; i++) {
                Annotation[] annotations = method.getParameterAnnotations()[i];

                if (annotations.length == 0) {
                    param = args[i];
                    paramType = method.getParameterTypes()[i];
                }

                for (Annotation annotation : annotations) {
                    if (annotation instanceof DynamicUrl) {
                        if (args[i] instanceof String) {
                            serverUrl = (String) args[i];
                            if (((DynamicUrl) annotation).useMethodEndPoint()) {
                                serverUrl = serverUrl + "/" + method.getName();
                            }
                            break;
                        } else {
                            throw new IMException("Dynamic url must be String type");
                        }
                    }
                }
            }
            try {
                if (reqAnnotation != null && reqAnnotation.method().equals(Request.GET)) {
                    resp = HttpClient.get(okHttpClient, serverUrl);
                } else {
                    if (args == null || args.length > 2 || param == null || paramType == null) {
                        throw new IllegalArgumentException(VALIDATION_FAIL.getDesc());
                    }

                    JsonObject jsonObject = new JsonObject();
                    for (Field field : paramType.getDeclaredFields()) {
                        field.setAccessible(true);
                        jsonObject.put(field.getName(), field.get(param));
                    }

                    resp = HttpClient.post(okHttpClient, jsonObject.toString(), url);
                }

                if (method.getReturnType() == void.class) {
                    return null;
                }

                String json = resp.body().string();
                Type _type = getGenericType(method);

                if (_type == null) {
                    return objectMapper.readValue(json, method.getReturnType());
                } else {
                    return objectMapper
                            .readValue(json, objectMapper.getTypeFactory()
                                    .constructParametricType(method.getReturnType(), objectMapper
                                            .getTypeFactory()
                                            .constructType(_type)));
                }
            } finally {
                if (resp != null) {
                    resp.body().close();
                }
            }
        }
    }
}
