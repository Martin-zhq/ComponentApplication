package cn.xxt.customsdk.okhttp.exception;

/**
 * 自定义异常类型，继承自Exception。
 * @author zhq
 * @date 2018/11/14 下午4:43
 */
public class OkHttpException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器返回的异常码
     */
    private int ecode;

    /**
     * 服务器返回的异常信息
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public Object getEmsg() {
        return emsg;
    }

    public void setEmsg(Object emsg) {
        this.emsg = emsg;
    }
}
