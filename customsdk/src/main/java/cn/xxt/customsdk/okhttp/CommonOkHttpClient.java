package cn.xxt.customsdk.okhttp;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.xxt.customsdk.okhttp.https.HttpsUtil;
import cn.xxt.customsdk.okhttp.listener.DisposeDataHandler;
import cn.xxt.customsdk.okhttp.response.CommonJsonCallback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 1、请求的发送
 * 2、请求参数的配置
 * 3、https的支持
 * @author zhq
 * @date 2018/11/7 下午7:45
 */
public class CommonOkHttpClient {

    /** 超时时间 30s */
    private static final int TIME_OUT = 30;

    private static OkHttpClient okHttpClient;

    //静态语句块 为client去配置参数
    static {
        //创建client对象的构建者
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //为构建者填充参数
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //允许请求重定向，默认就是为true
        okHttpBuilder.followRedirects(true);
        //添加https支持
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        //支持所有类型的https证书
        okHttpBuilder.sslSocketFactory(HttpsUtil.initSSLSocketFactory(), HttpsUtil.initTrustManager());
        //生成client对象
        okHttpClient = okHttpBuilder.build();
    }

    /**
     * 发送具体的http/https请求
     * @param request
     * @param commonJsonCallback
     * @return
     */
    public static Call sendRequest(Request request, CommonJsonCallback commonJsonCallback) {

        Call call = okHttpClient.newCall(request);

        call.enqueue(commonJsonCallback);

        return call;
    }


    public static Call get(Request request, DisposeDataHandler handler) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handler));
        return call;
    }

    public static Call post(Request request, DisposeDataHandler handler) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handler));
        return call;
    }

















}


