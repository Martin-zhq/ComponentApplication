package cn.xxt.customsdk.okhttp.request;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 封装所有的请求参数到一个hashMap中，方便后续使用
 * @author zhq
 * @date 2018/11/7 下午7:01
 */
public class RequestParams {

    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();

    public RequestParams() {
        this((Map<String, String>)null);
    }

    public RequestParams(Map<String, String> source) {
        if (null != source) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }


    public void put(String key, String value) {
        if (null != key && null != value) {
            urlParams.put(key, value);
        }
    }


    public void put(String key, Object object) throws FileNotFoundException {
        if (null != key) {
            fileParams.put(key, object);
        }
    }

    public boolean hasParams() {
        if (urlParams.size() > 0 || fileParams.size() > 0) {
            return true;
        }

        return false;
    }
}
