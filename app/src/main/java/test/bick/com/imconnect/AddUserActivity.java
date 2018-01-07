package test.bick.com.imconnect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

public class AddUserActivity extends AppCompatActivity   {

    private EditText add_et_user;
    private EditText add_et_yunyin;
    private Button btn_addd_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        add_et_user=findViewById(R.id.add_et_user);
        add_et_yunyin=findViewById(R.id.add_et_yunyin);
        btn_addd_user=findViewById(R.id.btn_addd_user);

        btn_addd_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_btn_user();
            }
        });

        // 获取当前用户名   String currentUser = EMClient.getInstance().getCurrentUser();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 添加好友
     * @param
     */
    public void add_btn_user() {

        //判断
        if(TextUtils.isEmpty(add_et_user.getText().toString().trim())  ){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //判断s
        else if(TextUtils.isEmpty(add_et_yunyin.getText().toString().trim())  ){
            Toast.makeText(this,"你的理由呢？",Toast.LENGTH_SHORT).show();
            return;
        }else {

            //成功
            //参数为要添加的好友的username和添加理由
            try {
 EMClient.getInstance().contactManager().addContact(add_et_user.getText().toString().trim(), add_et_yunyin.getText().toString().trim());

                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /**
                             * 发送广播
                             */
                            Intent  intent = new Intent();
                            intent.putExtra("add_et_user", add_et_user.getText().toString().trim());
                            intent.putExtra("add_et_yunyin", add_et_yunyin.getText().toString().trim());
                            intent.setAction("com.bick.ponymusic_master.service");
                            sendBroadcast(intent);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                //参数为要添加的好友的username和添加理由
                Toast.makeText(this,"发送添加成功~",Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



}
