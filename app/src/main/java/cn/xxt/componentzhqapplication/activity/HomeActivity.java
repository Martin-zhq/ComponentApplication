package cn.xxt.componentzhqapplication.activity;

import android.os.Bundle;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.activity.base.BaseActivity;

/**
 * 创建首页所有的Fragment
 * @author zhq
 * @date 2018/11/6 下午6:27
 */
public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
}
