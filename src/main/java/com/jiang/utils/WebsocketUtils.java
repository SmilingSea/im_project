package com.jiang.utils;

import java.net.InetSocketAddress;
import java.lang.reflect.Field;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;

/**
 * websocket工具类
 * @author SmilingSea
 * 2024/4/14
 */

public class WebsocketUtils {

    /**
     * 获取id
     * @param session
     * @return
     */
    public static InetSocketAddress getRemoteAddress(Session session) {
        if (session == null) {
            return null;
        }
        Async async = session.getAsyncRemote();

        // 在Tomcat 8.0.x版本有效
        // InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#sos#socketWrapper#socket#sc#remoteAddress");
        // 在Tomcat 8.5以上版本有效
        InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async, "base#socketWrapper#socket#sc#remoteAddress");
        return addr;
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }
        return null;
    }

}
