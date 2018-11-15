package cn.xxt.customsdk.okhttp.listener;

/**
 * 自定义事件监听
 * @author zhq
 * @date 2018/11/14 下午4:00
 */
public interface DisposeDataListener {

    /**
     * 请求成功的回调
     * @param responseObj
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败的回调
     * @param reasonObj
     */
    public void onFailure(Object reasonObj);

}
