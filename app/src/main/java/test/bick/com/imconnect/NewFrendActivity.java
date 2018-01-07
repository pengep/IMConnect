package test.bick.com.imconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import test.bick.com.imconnect.adapter.NewFreadAda;
import test.bick.com.imconnect.bean.NewFrient;

public class NewFrendActivity extends AppCompatActivity implements EMContactListener {

    private ListView new_lv;
    private List<NewFrient> list;
    private NewFreadAda ada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_frend);

        list=new ArrayList<>();

        initVeiw();

    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().contactManager().setContactListener(this);
    }

    private void initVeiw() {
        new_lv=findViewById(R.id.new_lv);
    }

    /**
     * 刷新适配器
     */
    private void initRecycle() {

        if(ada==null){
            ada = new NewFreadAda(this,list);
            new_lv.setAdapter(ada);
        }else {
            ada.notifyDataSetChanged();
        }
    }

    @Override
    public void onContactInvited(String username, String reason) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewFrendActivity.this,"你被删除！",Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("sssssssssshoushoushou"+username);
        //收到好友邀请
        list.add(new NewFrient(username,reason));
        initRecycle();
    }

    @Override
    public void onContactDeleted(String username) {
        //被删除时回调此方法
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewFrendActivity.this,"你被删除！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onContactAdded(String username) {
        //增加了联系人时回调此方法
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewFrendActivity.this,"增加联系人！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFriendRequestAccepted(final String s) {
        //好友请求被同意
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewFrendActivity.this,"你被同意！"+s,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFriendRequestDeclined(String s) {
        //好友请求被拒绝
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(NewFrendActivity.this,"你被拒绝！",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
