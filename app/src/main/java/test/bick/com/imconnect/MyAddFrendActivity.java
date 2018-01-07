package test.bick.com.imconnect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

public class MyAddFrendActivity extends AppCompatActivity {

    private ListView add_frend_lv;
    private MyReceivera receiver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_frend);

        add_frend_lv=findViewById(R.id.add_frend_lv);

        startBroadcastReceiver();

    }

    /**
     * 广播注册 ************************************
     */
    private void startBroadcastReceiver() {
        //注册广播接收器
        receiver=new  MyReceivera();
        IntentFilter filter=new IntentFilter();
        filter.addAction("com.bick.ponymusic_master.service");
        registerReceiver(receiver,filter);
    }
    /**
     * 所有接收到的信息 吼吼吼 ***
     */
    public class MyReceivera extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();

            String user=bundle.getString("add_et_user");
            String yuanyin=bundle.getString("add_et_yunyin");

            Toast.makeText(MyAddFrendActivity.this,"广播收到消息了"+user,Toast.LENGTH_SHORT).show();

        }
    }

}
