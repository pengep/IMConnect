package test.bick.com.imconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class MainActivity extends AppCompatActivity {

    private EditText editText_name;
    private EditText editText_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_name=findViewById(R.id.et_Zhu_name);
        editText_pwd=findViewById(R.id.et_Zhu_pwd);

    }

    /**
     * 注册
     * @param view
     */
    public void zhu_Liji(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(editText_name.getText().toString().trim(), editText_pwd.getText().toString().trim());//同步方法
                    Log.e("ZhuceShibai","注册成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"注册成功,请登录~",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("ZhuceShibai","注册失败"+e.getErrorCode()+e.getDescription());
                }
            }
        }).start();
    }
}
