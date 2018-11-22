package cn.xxt.componentzhqapplication.view.fragment.home;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.adapter.CourseAdapter;
import cn.xxt.componentzhqapplication.module.recommand.BaseRecommandModel;
import cn.xxt.componentzhqapplication.network.http.RequestCenter;
import cn.xxt.componentzhqapplication.view.fragment.BaseFragment;
import cn.xxt.customsdk.okhttp.listener.DisposeDataListener;

/**
 *
 * @author zhq
 * @date 2018/11/6 下午7:50
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener{

    /** 二维码 */
    private TextView tvQrcode;

    /** 种类？ */
    private TextView tvCatagory;

    /** 搜索栏 */
    private TextView tvSearch;

    /** 加载动画 */
    private ImageView ivLoadingAnim;

    /** 主列表 */
    private ListView lvHomeFragment;

    /** 总布局 */
    private View contentView;


    /** 数据 */
    private BaseRecommandModel baseRecommandModel;

    private CourseAdapter courseAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCommonData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        context = getActivity();
        contentView = inflater.inflate(R.layout.layout_home_fragment, null);
        initView();
        return contentView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            default:
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void initView() {
        tvQrcode = contentView.findViewById(R.id.tv_qrcode);
        tvQrcode.setOnClickListener(this);

        tvCatagory = contentView.findViewById(R.id.tv_category);
        tvCatagory.setOnClickListener(this);

        tvSearch = contentView.findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(this);

        ivLoadingAnim = contentView.findViewById(R.id.iv_loading_anim);
        //启动加载动画
        AnimationDrawable animationDrawable = (AnimationDrawable) ivLoadingAnim.getDrawable();
        animationDrawable.start();

        lvHomeFragment = contentView.findViewById(R.id.lv_home_fragment);
        lvHomeFragment.setOnItemClickListener(this);
    }


    /**
     * 数据请求
     */
    private void requestCommonData() {
        RequestCenter.requestHomeFragmentData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                baseRecommandModel = (BaseRecommandModel)responseObj;
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        });
    }

    /**
     * 数据请求成功后调用的方法
     */
    private void showSuccessView() {
        //数据判空
        if (null != baseRecommandModel.data.list && baseRecommandModel.data.list.size() > 0) {
            ivLoadingAnim.setVisibility(View.GONE);
            lvHomeFragment.setVisibility(View.VISIBLE);

            courseAdapter = new CourseAdapter(context, baseRecommandModel.data.list);
            lvHomeFragment.setAdapter(courseAdapter);
        } else {
            //数据异常
            showErrorView();
        }
    }

    /**
     * 数据异常
     */
    private void showErrorView() {

    }
}
