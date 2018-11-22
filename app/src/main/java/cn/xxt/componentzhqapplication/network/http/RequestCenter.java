package cn.xxt.componentzhqapplication.network.http;

import cn.xxt.componentzhqapplication.module.recommand.BaseRecommandModel;
import cn.xxt.customsdk.okhttp.CommonOkHttpClient;
import cn.xxt.customsdk.okhttp.listener.DisposeDataHandler;
import cn.xxt.customsdk.okhttp.listener.DisposeDataListener;
import cn.xxt.customsdk.okhttp.request.CommonRequest;
import cn.xxt.customsdk.okhttp.request.RequestParams;

/**
 * 封装所有的页面的网络请求到此类中
 */
public class RequestCenter {

    private static void postRequest(String url, RequestParams params, DisposeDataListener listener,
                                   Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params),
                new DisposeDataHandler(listener, clazz));
    }

    public static void requestHomeFragmentData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener,
                BaseRecommandModel.class);
    }



















































































}
