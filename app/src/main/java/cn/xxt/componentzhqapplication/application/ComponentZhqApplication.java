package cn.xxt.componentzhqapplication.application;

import android.app.Application;
/**
 * application的作用：
 * 1、整个程序的入口
 * 2、初始化工作
 * 3、为整个应用的其他模块提供上下文
 * @author zhq
 * @date 2018/11/5 下午8:15
 */
public class ComponentZhqApplication extends Application{

    private static ComponentZhqApplication application = null;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static ComponentZhqApplication getInstance() {
        return application;
    }
}
