package cn.xxt.customsdk.okhttp.response;


import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.IOException;

import cn.xxt.customsdk.okhttp.exception.OkHttpException;
import cn.xxt.customsdk.okhttp.listener.DisposeDataHandler;
import cn.xxt.customsdk.okhttp.listener.DisposeDataListener;
import cn.xxt.customsdk.util.ResponseEntityToModule;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CommonJsonCallback implements Callback {

    /** 此处与服务器返回的字段的是对应的 */
    private final String RESULT_CODE = "ecode";

    private final int RESULT_CODE_VALUE = 0;

    private final String ERROR_MSG = "emsg";

    private final String EMPTY_MSG = "";

    /** 自定义异常类型 */
    private final int NETWORK_ERROR = -1;

    private final int JSON_ERROR = -2;

    private final int OTHER_ERROR = -3;

    /** 进行消息的转发处理 */
    private Handler deliveryHandler;

    private DisposeDataListener listener;

    private Class<?> mClass;


    public CommonJsonCallback(DisposeDataHandler handler) {
        this.listener = handler.disposeDataListener;
        this.mClass = handler.mClass;
        //初始化出来的这个handler可以切换线程到主线程
        this.deliveryHandler = new Handler(Looper.getMainLooper());
    }


    /**
     * 请求失败
     * @param call
     * @param e
     */
    @Override
    public void onFailure(Call call, final IOException e) {
        //直接通过deliveryHandler将这个异常传给主线程的应用层处理
        deliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                throwExceptionOut(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) {
        try {
            //先获取响应中的数据的字符串
            final String result = response.body().string();
            //同样将其发送到主线程的业务层去处理响应结果
            deliveryHandler.post(new Runnable() {
                @Override
                public void run() {
                    handleResponse(result);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理响应数据的方法
     * @param responseObj
     */
    private void handleResponse(Object responseObj) {
        //先判断数据的有效性，来保证代码健壮性
        if (null == responseObj || ("").equals(responseObj.toString().trim())) {
            throwExceptionOut(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        //开始处理
        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
                //响应码为0 则成功
                if (RESULT_CODE_VALUE == result.getInt(RESULT_CODE)) {

                    if (null == mClass) {
                        //不需要转换成实体类，直接将结果回调到业务层
                        if (null != listener) {
                            listener.onSuccess(responseObj);
                        }
                    } else {
                        //需要转换成实体类
                        Object obj = ResponseEntityToModule.parseJsonObjectToModule(result, mClass);
                        if (null != obj) {
                            //转换成了正确的实体
                            if (null != listener) {
                                listener.onSuccess(obj);
                            }
                        } else {
                            throwExceptionOut(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                } else {
                    throwExceptionOut(new OkHttpException(OTHER_ERROR, result.get(RESULT_CODE)));
                }
            } else {
                throwExceptionOut(new OkHttpException(OTHER_ERROR, "服务器异常"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throwExceptionOut(new OkHttpException(OTHER_ERROR, e.getMessage()));
        }
    }

    /**
     * 简单封装回调异常的方法
     * @param exception
     */
    private void throwExceptionOut(Object exception) {
        if (null != listener) {
            listener.onFailure(exception);
        }
    }
}
