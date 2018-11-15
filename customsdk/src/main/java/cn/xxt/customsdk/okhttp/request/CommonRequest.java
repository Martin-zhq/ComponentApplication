package cn.xxt.customsdk.okhttp.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 接收请求参数，生成request对象
 * @author zhq
 * @date 2018/11/7 下午7:19
 */
public class CommonRequest {

    /**
     *
     * @param url
     * @param params
     * @return 返回创建好的post类型的request对象
     */
    public static Request createPostRequest(String url, RequestParams params) {

        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (null != params) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                //遍历添加请求参数
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        //通过请求构建类的build方法获取真正的请求体对象
        FormBody formBody = formBodyBuilder.build();

        return new Request.Builder().url(url).post(formBody).build();
    }


    /**
     *
     * @param url
     * @param params
     * @return 返回GET类型的request
     */
    public static Request createGetRequest(String url, RequestParams params) {

        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (null != params) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue())
                        .append("&");
            }
        }

        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() - 1))
                .get().build();
    }
}
