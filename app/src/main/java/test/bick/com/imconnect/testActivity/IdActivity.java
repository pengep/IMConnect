package test.bick.com.imconnect.testActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import test.bick.com.imconnect.HomeActivity;
import test.bick.com.imconnect.R;

public class IdActivity extends AppCompatActivity {

    private EditText frendUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);

        frendUserId=findViewById(R.id.frendUserName);
    }

    /**
     * 退出登录
     * @param view
     */
    public void tuichuLogin(View view) {

        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                System.out.println("ttttttttttttttt");
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 发起聊天
     * @param view
     */
    public void faqiChat(View view) {

        Intent in=new Intent(this, HomeActivity.class);
        //in.putExtra("userName",frendUserId.getText().toString().trim());
        startActivity(in);
    }

}
