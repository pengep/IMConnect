package test.bick.com.imconnect;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends AppCompatActivity {

    private EditText et_Login_name;
    private EditText et_Login_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_Login_name=findViewById(R.id.et_Login_name0);
        et_Login_pwd=findViewById(R.id.et_Login_pwd0);
    }

    public void login_zhuce0(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }


    /**
     * 登录
     * @param view
     */
    @SuppressLint("NewApi")
    public void login_Login0(View view) {

        String name = et_Login_name.getText().toString().trim();
        String pwd = et_Login_pwd.getText().toString().trim();

        final ProgressDialog builder = new ProgressDialog(this);
        builder.setCancelable(false);
        builder.setInverseBackgroundForced(false);
        builder.setMessage("正在登录中~") ;
        builder.create();
        builder.show();
        EMClient.getInstance().login(name, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups(); //组
//                EMClient.getInstance().chatManager().loadAllConversations();//n. 聊天；闲谈
                 runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(builder!=null&&builder.isShowing()){
                            builder.dismiss();
                        }
                        Toast.makeText(LoginActivity.this,"登录成功，嘎嘎嘎~",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }
                });
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onError(int i, String s) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(builder!=null&&builder.isShowing()){
                            builder.dismiss();
                        }
                        Toast.makeText(LoginActivity.this,"登录聊天服务器失败啊啊",Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("main", "登录聊天服务器失败啊啊！");
            }

            @Override
            public void onProgress(int i, String s) {

                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }
}
