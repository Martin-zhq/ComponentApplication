package cn.xxt.customsdk.okhttp.listener;
/**
 * 封装DisposeDataListener和要转换的实体类的字节码
 * @author zhq
 * @date 2018/11/14 下午4:04
 */
public class DisposeDataHandler {

    public DisposeDataListener disposeDataListener = null;

    public Class<?> mClass = null;

    public DisposeDataHandler(DisposeDataListener disposeDataListener) {
        this.disposeDataListener = disposeDataListener;
    }

    public DisposeDataHandler(DisposeDataListener disposeDataListener, Class<?> mClass) {
        this.disposeDataListener = disposeDataListener;
        this.mClass = mClass;
    }
}
