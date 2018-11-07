package cn.xxt.componentzhqapplication.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xxt.componentzhqapplication.R;
import cn.xxt.componentzhqapplication.activity.base.BaseActivity;
import cn.xxt.componentzhqapplication.view.fragment.home.HomeFragment;
import cn.xxt.componentzhqapplication.view.fragment.home.MessageFragment;
import cn.xxt.componentzhqapplication.view.fragment.home.MineFragment;

/**
 * 创建首页所有的Fragment
 * @author zhq
 * @date 2018/11/6 下午6:27
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private FragmentManager fragmentManager;

    private HomeFragment homeFragment;

    private MessageFragment messageFragment;

    private MineFragment mineFragment;


    private RelativeLayout rlContent;

    private RelativeLayout rlHome;

    private TextView tvHomeIcon;

    private RelativeLayout rlMessage;

    private TextView tvMessageIcon;

    private RelativeLayout rlMine;

    private TextView tvMineIcon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(homeFragment, transaction);
        hideFragment(messageFragment, transaction);
        hideFragment(mineFragment, transaction);
        switch (view.getId()) {
            case R.id.rl_home:
                tvHomeIcon.setBackgroundResource(R.drawable.comui_tab_home_selected);
                tvMessageIcon.setBackgroundResource(R.drawable.comui_tab_message);
                tvMineIcon.setBackgroundResource(R.drawable.comui_tab_person);

                if (null == homeFragment) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.rl_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;

            case R.id.rl_message:
                tvHomeIcon.setBackgroundResource(R.drawable.comui_tab_home);
                tvMessageIcon.setBackgroundResource(R.drawable.comui_tab_message_selected);
                tvMineIcon.setBackgroundResource(R.drawable.comui_tab_person);

                if (null == messageFragment) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.rl_content, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;

            case R.id.rl_mine:
                tvHomeIcon.setBackgroundResource(R.drawable.comui_tab_home);
                tvMessageIcon.setBackgroundResource(R.drawable.comui_tab_message);
                tvMineIcon.setBackgroundResource(R.drawable.comui_tab_person_selected);

                if (null == mineFragment) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.rl_content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
            default:
        }

        transaction.commit();
    }


    private void initView() {

        rlContent = findViewById(R.id.rl_content);
        rlHome = findViewById(R.id.rl_home);
        tvHomeIcon = findViewById(R.id.tv_home_icon);
        rlMessage = findViewById(R.id.rl_message);
        tvMessageIcon = findViewById(R.id.tv_message_icon);
        rlMine = findViewById(R.id.rl_mine);
        tvMineIcon = findViewById(R.id.tv_mine_icon);

        rlHome.setOnClickListener(this);
        rlMessage.setOnClickListener(this);
        rlMine.setOnClickListener(this);

        if (null == fragmentManager) {
            fragmentManager = getSupportFragmentManager();
        }
        //初始化显示homeFragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (null == homeFragment) {
            homeFragment = new HomeFragment();
            tvHomeIcon.setBackgroundResource(R.drawable.comui_tab_home_selected);
            tvMessageIcon.setBackgroundResource(R.drawable.comui_tab_message);
            tvMineIcon.setBackgroundResource(R.drawable.comui_tab_person);
        }
        transaction.replace(R.id.rl_content, homeFragment);
        transaction.commit();
    }

    private void hideFragment(Fragment fragment, FragmentTransaction transaction) {
        if (null != fragment) {
            transaction.hide(fragment);
        }
    }
}
