package cn.xxt.componentzhqapplication.view.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.view.fragment.BaseFragment;
/**
 *
 * @author zhq
 * @date 2018/11/6 下午7:50
 */
public class HomeFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_home_fragment, null);
        return view;
    }
}
