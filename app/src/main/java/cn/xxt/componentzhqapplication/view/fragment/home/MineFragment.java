package cn.xxt.componentzhqapplication.view.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.view.fragment.BaseFragment;

public class MineFragment extends BaseFragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mine_fragment, null);
        return view;
    }
}
