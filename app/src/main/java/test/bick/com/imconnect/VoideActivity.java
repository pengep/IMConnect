package test.bick.com.imconnect;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

public class VoideActivity extends AppCompatActivity {

    private SurfaceView voide_sufv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voide);

        voide_sufv=findViewById(R.id.voide_sufv);
        initData();

    }

    private void initData() {


    }
}
