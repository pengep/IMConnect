package test.bick.com.imconnect;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.NetUtils;

import test.bick.com.imconnect.fragment.FragmentShezhi;
import test.bick.com.imconnect.fragment.FragmentTongxunLu;
import test.bick.com.imconnect.fragment.FragmentXiaoxiLie;
import test.bick.com.imconnect.fragment.FragmentZhibo;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton iv_xiaoxi;
    private RadioButton iv_tongxun;
    private RadioButton iv_zhibo;
    private RadioButton iv_shezhi;
    private ViewPager vp_main;
    private TextView zhu_add_haoyou;
    private String userName;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //userName = getIntent().getStringExtra("userName");


        initView();
        initLinsenter();

        iv_xiaoxi.setTextColor(getResources().getColor(R.color.home_text_select));
        iv_tongxun.setTextColor(getResources().getColor(R.color.home_text_nomore));
        iv_zhibo.setTextColor(getResources().getColor(R.color.home_text_nomore));
        iv_shezhi.setTextColor(getResources().getColor(R.color.home_text_nomore));
        //代码实现
        Drawable drawable = this.getResources().getDrawable(R.mipmap.xiaoxilv);
        iv_xiaoxi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);


        initData();

    }

    private void initLinsenter() {
        iv_xiaoxi.setOnClickListener(this);
        iv_tongxun.setOnClickListener(this);
        iv_zhibo.setOnClickListener(this);
        iv_shezhi.setOnClickListener(this);
        zhu_add_haoyou.setOnClickListener(this);
    }

    private void initView() {
        iv_xiaoxi=findViewById(R.id.iv_xiaoxi);
        iv_tongxun=findViewById(R.id.iv_tongxun);
        iv_zhibo=findViewById(R.id.iv_zhibo);
        iv_shezhi=findViewById(R.id.iv_shezhi);
        vp_main=findViewById(R.id.vp_main);
        zhu_add_haoyou=findViewById(R.id.zhu_add_haoyou);

    }

    private void initData() {
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());
        huadong();
    }

    private void huadong() {

        /**
         * ViewPager 保活 默认加载三个页面，这个设置 当第四个页面, 不销毁第一个页面
         */
        vp_main.setOffscreenPageLimit(2);

        MyFragmentPagerAdapter ada=new MyFragmentPagerAdapter(getSupportFragmentManager());
        vp_main.setAdapter(ada);

        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 只要滑动 就监听
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 滑动改变
             * @param position
             */
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        iv_xiaoxi.setTextColor(getResources().getColor(R.color.home_text_select));
                        iv_tongxun.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_zhibo.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_shezhi.setTextColor(getResources().getColor(R.color.home_text_nomore));

                        //代码实现 DrawableTop
                        Drawable drawable1 = getResources().getDrawable(R.mipmap.xiaoxilv);
                        iv_xiaoxi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable1, null, null);
                        //代码实现
                        Drawable drawable2 =  getResources().getDrawable(R.mipmap.tongxunluhh);
                        iv_tongxun.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable2, null, null);
                        //代码实现
                        Drawable drawable3 = getResources().getDrawable(R.mipmap.zhibohh);
                        iv_zhibo.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable3, null, null);
                        //代码实现
                        Drawable drawable4 =  getResources().getDrawable(R.mipmap.userhh);
                        iv_shezhi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable4, null, null);

                        break;
                    case 1:
                        iv_xiaoxi.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_tongxun.setTextColor(getResources().getColor(R.color.home_text_select));
                        iv_zhibo.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_shezhi.setTextColor(getResources().getColor(R.color.home_text_nomore));

                        //代码实现 DrawableTop
                        Drawable drawable11 = getResources().getDrawable(R.mipmap.xiaoxihh);
                        iv_xiaoxi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable11, null, null);
                        //代码实现
                        Drawable drawable22 =  getResources().getDrawable(R.mipmap.tongxunlulv);
                        iv_tongxun.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable22, null, null);
                        //代码实现
                        Drawable drawable33 = getResources().getDrawable(R.mipmap.zhibohh);
                        iv_zhibo.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable33, null, null);
                        //代码实现
                        Drawable drawable44 =  getResources().getDrawable(R.mipmap.userhh);
                        iv_shezhi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable44, null, null);

                        break;
                    case 2:
                        iv_xiaoxi.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_tongxun.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_zhibo.setTextColor(getResources().getColor(R.color.home_text_zhibo));
                        iv_shezhi.setTextColor(getResources().getColor(R.color.home_text_nomore));

                        //代码实现 DrawableTop
                        Drawable drawable21 = getResources().getDrawable(R.mipmap.xiaoxihh);
                        iv_xiaoxi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable21, null, null);

                        //代码实现
                        Drawable drawablef22 =  getResources().getDrawable(R.mipmap.tongxunluhh);
                        iv_tongxun.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawablef22, null, null);
                        //代码实现
                        Drawable drawable23 = getResources().getDrawable(R.mipmap.zhibolvhong);
                        iv_zhibo.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable23, null, null);
                        //代码实现
                        Drawable drawable24 =  getResources().getDrawable(R.mipmap.userhh);
                        iv_shezhi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable24, null, null);

                        break;
                    case 3:
//                        iv_shezhi.setChecked(true);
                        iv_xiaoxi.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_tongxun.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_zhibo.setTextColor(getResources().getColor(R.color.home_text_nomore));
                        iv_shezhi.setTextColor(getResources().getColor(R.color.home_text_select));


                        //代码实现 DrawableTop
                        Drawable drawable31 = getResources().getDrawable(R.mipmap.xiaoxihh);
                        iv_xiaoxi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable31, null, null);
                        //代码实现
                        Drawable drawablef32 =  getResources().getDrawable(R.mipmap.tongxunluhh);
                        iv_tongxun.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawablef32, null, null);
                        //代码实现
                        Drawable drawablef33 = getResources().getDrawable(R.mipmap.zhibohh);
                        iv_zhibo.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawablef33, null, null);
                        //代码实现
                        Drawable drawable34 =  getResources().getDrawable(R.mipmap.userlv);
                        iv_shezhi.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable34, null, null);

                        break;
                    default:break;
                }

            }
            /**
             * 滑动改变
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_xiaoxi:
                vp_main.setCurrentItem(0);
                break;
            case R.id.iv_tongxun:
                vp_main.setCurrentItem(1);
                break;
            case R.id.iv_zhibo:
                vp_main.setCurrentItem(2);
                break;
            case R.id.iv_shezhi:
                vp_main.setCurrentItem(3);
                break;
            case R.id.zhu_add_haoyou:
                startActivity(new Intent(this,AddUserActivity.class));
                break;
        }
    }
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            switch (position){
                case 0:
                    fragment= new FragmentXiaoxiLie();
                    break;
                case 1:
                    fragment=new FragmentTongxunLu();
                    break;
                case 2:
                    fragment=new FragmentZhibo();
                    break;
                case 3:
                    fragment=new FragmentShezhi();
                    break;
                default:break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    /**
     *  回调监听
     */
    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }
        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if(error == EMError.USER_REMOVED){
                        // 显示帐号已经被移除
                        Toast.makeText(HomeActivity.this,"显示帐号已经被移除",Toast.LENGTH_SHORT).show();
                        finish();
                    }else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        Toast.makeText(HomeActivity.this,"显示帐号在其他设备登录",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        if (NetUtils.hasNetwork(HomeActivity.this)){
                            //连接不到聊天服务器
                            Toast.makeText(HomeActivity.this,"连接不到聊天服务器",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            //当前网络不可用，请检查网络设置
                            Toast.makeText(HomeActivity.this,"当前网络不可用，请检查网络设置",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}
