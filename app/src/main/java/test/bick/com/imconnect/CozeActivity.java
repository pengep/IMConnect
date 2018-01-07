package test.bick.com.imconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import test.bick.com.imconnect.adapter.GridCozeAdapter;
import test.bick.com.imconnect.adapter.MessageListAdapter;
import test.bick.com.imconnect.bean.MyBean;
import test.bick.com.imconnect.bean.XiaoxiPlusBean;

import static test.bick.com.imconnect.BuildConfig.DEBUG;

public class CozeActivity extends AppCompatActivity implements EMMessageListener, View.OnClickListener, View.OnTouchListener {

    private TextView mTvAvailableCharNum;

    private EditText coze_context;
    private RecyclerView recycler_view;
    private MessageListAdapter mMessageListAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<MyBean> list;
    private String userName;
    private String userNameTest;
    private ImageView yuyinjian,liao_img_finish;
    private SharedPreferences ztxiaoxi;
    private TextView liao_img_userName;

    private Button coze_context_yuyin_btn,xiaoxi_btn_send; //语音
    private ImageView xiaoxi_iv_biaoqing ,xiaoxi_btn_sendivjia;
    private int duration;
    private ViewPager liao_vp_qita;
    private ArrayList<XiaoxiPlusBean> listVp;

    private RelativeLayout sendxiall;
    private RelativeLayout.LayoutParams params;
    private String format;
    private GridCozeAdapter gridAda;

    private static final int REQUEST_CAMERA_CODE = 1;
    private static final int REQUEST_LIST_CODE = 0;
    private ArrayList<MyBean>  listPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_coze);
        userName = getIntent().getStringExtra("userllla");
        userNameTest =getIntent().getStringExtra("userName");
        coze_context=findViewById(R.id.coze_context);
        recycler_view=findViewById(R.id.recycler_view);
        yuyinjian=findViewById(R.id.xiaoxi_iv_yuyin);
        coze_context_yuyin_btn=findViewById(R.id.coze_context_yuyin_btn);
        xiaoxi_iv_biaoqing=findViewById(R.id.xiaoxi_iv_biaoqing);
        liao_img_userName=findViewById(R.id.liao_img_userName);
        liao_img_finish=findViewById(R.id.liao_img_finish);
        liao_vp_qita=findViewById(R.id.liao_vp_qita);//vp
        xiaoxi_btn_sendivjia=findViewById(R.id.xiaoxi_btn_sendivjia);
        xiaoxi_btn_send=findViewById(R.id.xiaoxi_btn_send);
        sendxiall=findViewById(R.id.sendxiall);
        liao_img_userName.setText(userName);

        mTvAvailableCharNum=findViewById(R.id.mTvAvailableCharNum);
        initLisenter();
        ztxiaoxi = getSharedPreferences("ztxiaoxi", MODE_PRIVATE);
        list=new ArrayList<>();
        listPhotoView =new ArrayList<>();
        init();
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        vpinitData();
        initPager();
        //huoquLiaoTianjilu();

        /**
         * OnTouch
         */
        coze_context_yuyin_btn.setOnTouchListener(this);

        /**
         * 12-28 06:00:00.793 30781-30781/test.bick.com.imconnect I/System.out: vvvvvvvvvvvvvvvvCMD
         12-28 07:16:00.372 32372-32372/test.bick.com.imconnect I/System.out: vvvvvvvvvvvvvvvvTXT
         12-28 07:16:10.596 32372-32372/test.bick.com.imconnect I/System.out: vvvvvvvvvvvvvvvvVOICE
         12-28 07:16:47.780 32372-32372/test.bick.com.imconnect I/System.out: vvvvvvvvvvvvvvvvTXT
         */

    }

    public void setGridada(GridView gv1){
        if(gridAda==null){
            gridAda = new GridCozeAdapter(listVp,this);
            gv1.setAdapter(gridAda);
        }else {
            gridAda.notifyDataSetChanged();
        }
        gridJianting( gv1);
    }

    /**
     * 设置监听
     */
    private void gridJianting(GridView gv1) {

        gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(listVp.get(position).name.equals("相册")){

                    //startActivity(new Intent(CozeActivity.this, XinagceActivity.class));

                    ISListConfig config = new ISListConfig.Builder()
                            .multiSelect(true)
                            // 是否记住上次选中记录
                            .rememberSelected(false)
                            // 使用沉浸式状态栏
                            .statusBarColor(Color.parseColor("#393A3F")).build();

                    ISNav.getInstance().toListActivity(CozeActivity.this, config, REQUEST_LIST_CODE);

                }else if(listVp.get(position).name.equals("拍摄")){
                    ISCameraConfig config = new ISCameraConfig.Builder()
                            .needCrop(true)
                            .cropSize(1, 1, 200, 200)
                            .build();

                    ISNav.getInstance().toCameraActivity(CozeActivity.this, config, REQUEST_CAMERA_CODE);
                }else if(listVp.get(position).name.equals("视频聊天")){
                    startActivity(new Intent(CozeActivity.this,VoideActivity.class));
                }

            }
        });
    }

    /**
     * 回调照片路径
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            final List<String> pathList = data.getStringArrayListExtra("result");
            // 测试Fresco
            // draweeView.setImageURI(Uri.parse("file://"+pathList.get(0)));
            /**
             * 发送图片
             */
            for (String path : pathList) {

                EMMessage imageSendMessage = EMMessage.createImageSendMessage(path, false, userName);
                //如果是群聊，设置chattype，默认是单聊
                EMClient.getInstance().chatManager().sendMessage(imageSendMessage);
                imageSendMessage.setMessageStatusCallback(new EMCallBack(){
                    @Override
                    public void onSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CozeActivity.this,"发送图片成功~",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

                /**
                 * 发宋图片成功
                 */
                if(list!=null){
                    MyBean bean= new MyBean();
                    bean.setP1(path);
                    bean.setPanduan(55); //发送图片
                    list.add(bean);

                    MyBean bean2= new MyBean();
                    bean2.setP1(path);
                    bean2.setP2Type(100);//发送
                    listPhotoView.add(bean2);
                }
                initRecycle();

            }

        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            // tvResult.append(path + "\n");

            EMMessage imageSendMessage = EMMessage.createImageSendMessage(path, false, userName);
            //如果是群聊，设置chattype，默认是单聊
            EMClient.getInstance().chatManager().sendMessage(imageSendMessage);
            imageSendMessage.setMessageStatusCallback(new EMCallBack(){
                @Override
                public void onSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CozeActivity.this,"发送成功~",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }
    }

    private void init() {

        coze_context.addTextChangedListener(new EditChangedListener());
        /**
         * 动态设置宽高
         */
        params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的

        params.height = dip2px(CozeActivity.this, 50); //设值
        sendxiall.setLayoutParams(params);
    }

    private void vpinitData() {
        listVp = new ArrayList();
        listVp.add(new XiaoxiPlusBean(R.mipmap.xiangce,"相册"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.paishe,"拍摄"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.shipin,"视频聊天"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.weizhi,"位置"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.hongbao,"红包"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.zhuanzhang,"转账"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.yuyinxiaoxi,"语音输入"));
        listVp.add(new XiaoxiPlusBean(R.mipmap.mingpian,"名片"));

        listVp.add(new XiaoxiPlusBean(R.mipmap.shoucang,"收藏"));

    }

    private void initPager() {

        liao_vp_qita.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            /**
             * ViewGroup 需要 往容器里面 添加 ViewGroup .add
             * @param container
             * @param position
             * @return
             */
            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                if(position==0){
                    /**
                     * ViewPager1
                     */
                    View view=View.inflate(CozeActivity.this,R.layout.xiaoxi_plus_vp1,null);
                    GridView gv1=view.findViewById(R.id.xiaoxi_p_gv1);

                    setGridada(gv1); //设置适配器
                    container.addView(view);
                    return view;
                }else {
                    /**
                     * ViewPager2
                     */
                    View view=View.inflate(CozeActivity.this,R.layout.xiaoxi_plus_vp2,null);
                    GridView gv2=view.findViewById(R.id.xiaoxi_p_gv2);
                    gv2.setAdapter(new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return 1;
                        }

                        @Override
                        public Object getItem(int position) {
                            return listVp.get(listVp.size()-1);
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            if(convertView==null){
                                convertView=View.inflate(CozeActivity.this,R.layout.item_vp1_xiaoxi,null);
                                ImageView iv= convertView.findViewById(R.id.item_vi1_iv);
                                TextView tv= convertView.findViewById(R.id.item_vi1_tv);

                                iv.setImageResource(listVp.get(listVp.size()-1).iv);
                                tv.setText(listVp.get(listVp.size()-1).name);
                            }
                            return convertView;
                        }
                    });
                    container.addView(view);
                    return view;
                }

            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

                //vp 删除调用super 方法
                container.removeView((View) object);
            }
        });
    }


    private void initLisenter() {
        yuyinjian.setOnClickListener(this);
        xiaoxi_iv_biaoqing.setOnClickListener(this);
        liao_img_finish.setOnClickListener(this);
        // 加号
        xiaoxi_btn_sendivjia.setOnClickListener(this);
        xiaoxi_btn_sendivjia.setOnTouchListener(this);
        params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的
        coze_context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        coze_context.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean b = event.getAction() == MotionEvent.ACTION_DOWN;
                if(b){
                    InputMethodManager imma = (InputMethodManager) CozeActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imma.showSoftInput(coze_context, InputMethodManager.RESULT_SHOWN);
                    imma.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                            InputMethodManager.HIDE_IMPLICIT_ONLY);

                    /**
                     * 关闭软键盘
                     */
                    if(params.height==250){
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        //得到InputMethodManager的实例
                        if (imm.isActive()) {
                            //如果开启
                            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
                            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                        }
                    }
                    /**
                     * 动态设置宽高
                     */
                    params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的
                    params.height = dip2px(CozeActivity.this, 50);
                    sendxiall.setLayoutParams(params);
                    liao_vp_qita.setVisibility(View.INVISIBLE); //关闭 vp

                }
                return false;
            }
        });
    }


    /**
     * 获取聊天记录
     */
    private void huoquLiaoTianjilu() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(userName);
        //获取此会话的所有消息
        List<EMMessage> messages = conversation.getAllMessages();
        System.out.println("jjjjjjliaotianjilu"+messages.get(0).getBody().toString());
        //SDK初始化加载的聊天记录为20条，到顶时需要去DB里获取更多
        //获取startMsgId之前的pagesize条消息，此方法获取的messages SDK会自动存入到此会话中，APP中无需再次把获取到的messages添加到会话中
//        List<EMMessage> messages = conversation.loadMoreMsgFromDB(startMsgId, pagesize);
    }

    @Override
    public void onBackPressed() {
        /**
         * 动态设置宽高
         */
        params.height = dip2px(CozeActivity.this, 50);
        sendxiall.setLayoutParams(params);

        super.onBackPressed();
    }

    /**
     * 刷新适配器
     */
    private void initRecycle() {
        if(mLinearLayoutManager==null){
            mLinearLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(mLinearLayoutManager);
        }else {
            recycler_view.setLayoutManager(mLinearLayoutManager);
        }

        if(mMessageListAdapter==null){
            mMessageListAdapter = new MessageListAdapter( list,this);
            recycler_view.setAdapter(mMessageListAdapter);
        }else {
            mMessageListAdapter.notifyDataSetChanged();
        }
        recycler_view.scrollToPosition(list.size()-1);

        itemLinsenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);

    }

    /**
     * 发送消息
     * @param view
     */
    public void coze_send(View view) {

        String mes = coze_context.getText().toString().trim();

        if(TextUtils.isEmpty(coze_context.getText().toString().trim())){
            Toast.makeText(this,"你不输入内容,发个毛毛呀~",Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            //发送消息
            //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
            EMMessage message = EMMessage.createTxtSendMessage(mes,userName);
            //如果是群聊，设置chattype，默认是单聊
            message.setChatType(EMMessage.ChatType.Chat);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);

            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CozeActivity.this,"发送成功~",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {

                }
                @Override
                public void onProgress(int i, String s) {

                }
            });

            if(list!=null){
                MyBean bean=new MyBean();
                bean.setMessage(mes);
                bean.setPanduan(0);
                list.add(bean);
            }
            initRecycle();
            coze_context.setText(null);

        }
    }

    /**
     * 接收消息
     * @param messages
     */
    @Override
    public void onMessageReceived(final List<EMMessage> messages ) {
        //收到消息  1111111
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i <messages.size() ; i++) {

                    EMMessage.Type type = messages.get(i).getType(); //判断类型

                    //文本
                    if(type== EMMessage.Type.TXT){
                        MyBean bean=new MyBean();
                        EMTextMessageBody body = (EMTextMessageBody) (messages.get(i).getBody());
                        String message = body.getMessage();
                        bean.setMessage(message);
                        bean.setPanduan(1);
                        list.add(bean);
                    }
                    //语音
                    else if(type == EMMessage.Type.VOICE){

                        String urlPath = ((EMVoiceMessageBody) messages.get(i).getBody()).getLocalUrl();

                        int length = ((EMVoiceMessageBody) messages.get(i).getBody()).getLength();
                        MyBean bean=new MyBean();
                        bean.setY1(urlPath);
                        bean.setPanduan(44);
                        bean.setLength(length);
                        list.add(bean);
                    }
                    //视频
                    else if(type== EMMessage.Type.VIDEO){

                    }
                    //文件
                    else if(type== EMMessage.Type.FILE){

                    }
                    //图片
                    else if(type== EMMessage.Type.IMAGE){

                        EMImageMessageBody body = (EMImageMessageBody) messages.get(i).getBody();
                        String fileName = body.getRemoteUrl();
                        String localUrl = body.getLocalUrl();//本地路径

                        /**
                         * 接收图片成功
                         */
                        if(list!=null){
                            MyBean bean= new MyBean();
                            bean.setP1(fileName);
                            bean.setPanduan(66);
                            list.add(bean);

                            MyBean bean2= new MyBean();
                            bean2.setP1(fileName);
                            bean2.setP2Type(99);//接收
                            listPhotoView.add(bean2);
                        }
                    }

                    initRecycle(); //设置适配器
                }
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //切换语音
            case  R.id.xiaoxi_iv_yuyin:
                //true 是文字  false 是语音
                if(ztxiaoxi.getBoolean("isxiaoxi",true)==true){

                    /**
                     * 打开软键盘
                     */
                    InputMethodManager imm = (InputMethodManager)  CozeActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(coze_context, InputMethodManager.RESULT_SHOWN);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                            InputMethodManager.HIDE_IMPLICIT_ONLY);

                    //文字
                    yuyinjian.setImageResource(R.mipmap.yuyin2);

                    coze_context_yuyin_btn.setVisibility(View.GONE);
                    coze_context.setVisibility(View.VISIBLE);//显示EditText

                    ztxiaoxi.edit().putBoolean("isxiaoxi",false).commit();
                }else {
                    //语音
                    /**
                     * 关闭软键盘
                     */
                    InputMethodManager imm = (InputMethodManager) CozeActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(coze_context.getWindowToken(), 0);

                    /**
                     * 动态设置宽高
                     */
                    params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的
                    params.height = dip2px(CozeActivity.this, 50);
                    sendxiall.setLayoutParams(params);

                    coze_context_yuyin_btn.setVisibility(View.VISIBLE);//显示语音
                    coze_context.setVisibility(View.GONE);
                    yuyinjian.setImageResource(R.mipmap.jianpan);

                    ztxiaoxi.edit().putBoolean("isxiaoxi",true).commit();
                }
                break;
            case R.id.liao_img_finish:
                finish();
                break;
            case R.id.coze_context_yuyin_btn:

                break;
            //jiahao
            case R.id.xiaoxi_btn_sendivjia:

                break;
        }
    }

    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue)
    {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }


    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder = new MediaRecorder();
    private File audioFile;
    private String msg;


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        //触摸加号
        if(id==R.id.xiaoxi_btn_sendivjia){
            if(event.getAction()==MotionEvent.ACTION_UP) {

                //true shi 更多
                if (ztxiaoxi.getBoolean("ismore", true) == true) {
                    //更多

                    /**
                     * 关闭软键盘
                     */
                    InputMethodManager imm = (InputMethodManager) CozeActivity.this
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(coze_context.getWindowToken(), 0);

                    liao_vp_qita.setVisibility(View.VISIBLE); //vp
                    /**
                     * 动态设置宽高
                     */
                    params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的
                    params.height = dip2px(CozeActivity.this, 250);
                    sendxiall.setLayoutParams(params);


                    ztxiaoxi.edit().putBoolean("ismore", false).commit();
                } else {
                    //聊天
                    liao_vp_qita.setVisibility(View.INVISIBLE); //vp
                    params = (RelativeLayout.LayoutParams) sendxiall.getLayoutParams(); //提取出来了 全局的
                    params.height = dip2px(CozeActivity.this, 50);
                    sendxiall.setLayoutParams(params);

//                    /**
//                     * 打开软键盘
//                     */
//                    InputMethodManager imm = (InputMethodManager)  CozeActivity.this
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(coze_context, InputMethodManager.RESULT_SHOWN);
//                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
//                            InputMethodManager.HIDE_IMPLICIT_ONLY);

                    ztxiaoxi.edit().putBoolean("ismore", true).commit();
                }
            }
        }
        if(id==R.id.coze_context_yuyin_btn){

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    try {

                        Toast.makeText(this,"按下录音",Toast.LENGTH_SHORT).show();
                        // 设置音频来源(一般为麦克风)
                        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        // 设置音频输出格式（默认的输出格式）
                        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                        // 设置音频编码方式（默认的编码方式）
                        mediaRecorder
                                .setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                        // 创建一个临时的音频输出文件
                        audioFile = File.createTempFile("record_", ".amr");
                        mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
                        mediaRecorder.prepare();
                        mediaRecorder.start();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    /***
                     * 停止录音
                     */
                    Toast.makeText(this,"抬起",Toast.LENGTH_SHORT).show();
                    if (audioFile != null) {
                        mediaRecorder.stop();
                    }

                    /**
                     * 获取长度
                     */
                    if (audioFile != null) {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(audioFile.getAbsolutePath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            mediaPlayer.prepare();

                            //获取总长度
                            duration = mediaPlayer.getDuration();

                            //毫秒数转换为 date
                            Date currentTime = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                            currentTime.setTime(duration);
                            //fasong 时间转换
                            format = simpleDateFormat.format(currentTime);
                            Toast.makeText(this,"长度是:"+ format,Toast.LENGTH_SHORT).show();


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    /**
                     * 发送语音消息
                     */
                    //filePath为语音文件路径，length为录音时间(秒)
                    EMMessage message = EMMessage.createVoiceSendMessage(audioFile.getAbsolutePath(),duration , userName);
                    //如果是群聊，设置chattype，默认是单聊
                    message.setChatType(EMMessage.ChatType.Chat);
                    EMClient.getInstance().chatManager().sendMessage(message);

                    message.setMessageStatusCallback(new EMCallBack(){
                        @Override
                        public void onSuccess() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CozeActivity.this,"发送语音成功！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(int i, String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(CozeActivity.this,"发送失败~",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    /**
                     * 发宋语音成功
                     */
                    if(list!=null){
                        MyBean bean= new MyBean();
                        bean.setY1(format);
                        bean.setPanduan(33);
                        bean.setPathYY(audioFile.getAbsolutePath());
                        list.add(bean);
                    }
                    initRecycle();

                    break;
            }
        }

        return true;
    }

    /**
     * EditTextView 的监听 输入框监听 ，实时监听
     */
    class EditChangedListener implements TextWatcher {

        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (DEBUG)
                // Log.i(TAG, "输入文本之前的状态");
                temp = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (DEBUG)
                //  Log.i(TAG, "输入文字中的状态，count是一次性输入字符数");

                mTvAvailableCharNum.setText("还能输入" + (charMaxNum - s.length()) + "字符");
            if(s.length()>0){
                xiaoxi_btn_sendivjia.setVisibility(View.INVISIBLE);
                xiaoxi_btn_send.setVisibility(View.VISIBLE);//显示发送按钮
            }else {
                xiaoxi_btn_sendivjia.setVisibility(View. VISIBLE);//显示加号
                xiaoxi_btn_send.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (DEBUG)
            //Log.i(TAG, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
                editStart = coze_context.getSelectionStart();
            editEnd = coze_context.getSelectionEnd();

            if (temp.length() > charMaxNum) {
                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                coze_context.setText(s);
                coze_context.setSelection(tempSelection);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /**
         * 关闭 软键盘
         */
        InputMethodManager imm = (InputMethodManager) CozeActivity.this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(coze_context.getWindowToken(), 0);
    }

    /**
     * 回调监听 item
     */
    public void itemLinsenter(){
        //语音监听
        mMessageListAdapter.setLinsenter(new MessageListAdapter.VoiceLinsenter() {
            @Override
            public void sendVoice(String url) {
                try {
                    if (audioFile != null) {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        mediaPlayer
                                .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        setTitle("录音播放完毕.");
                                    }
                                });
                        Toast.makeText(CozeActivity.this,"正在播放录音fffff",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            @Override
            public void receiverVoice(String url) {
                try {
                    ///data/user/0/test.bick.com.imconnect/files/qwe/asd160/ccec99d0-ec56-11e7-877c-e7186ed5c365.amr
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mediaPlayer
                            .setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    setTitle("录音播放完毕.");
                                }
                            });
                    Toast.makeText(CozeActivity.this,"正在播放录音,咯咯咯~",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //图片监听
        mMessageListAdapter.setLinsenter(new MessageListAdapter.ImageLinsenter() {
            @Override
            public void sendImage(String url,int index) { //发送 路径在  本地

                Intent intent=new Intent(CozeActivity.this,PhotoViewActivity.class);
                intent.putParcelableArrayListExtra("photoList",listPhotoView);
                intent.putExtra("index",index);
                startActivity(intent);
            }

            @Override
            public void receiverImage(String url,int index) {//接收 路径在  网络
                Intent intent=new Intent(CozeActivity.this,PhotoViewActivity.class);
                intent.putParcelableArrayListExtra("photoList",listPhotoView);
                intent.putExtra("index",index);
                startActivity(intent);
            }
        });
    }
}
