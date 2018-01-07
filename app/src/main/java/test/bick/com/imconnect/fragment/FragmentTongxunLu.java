package test.bick.com.imconnect.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import java.util.List;

import test.bick.com.imconnect.CozeActivity;
import test.bick.com.imconnect.MyAddFrendActivity;
import test.bick.com.imconnect.NewFrendActivity;
import test.bick.com.imconnect.R;
import test.bick.com.imconnect.adapter.LIebiaoAdapter;
import view.xlistview.XListView;

/**
 * User:白二鹏
 * Created by Administrator-12-20 18 : 46
 */

public class FragmentTongxunLu extends Fragment {

    private View view;
    private Button xiaoxi_tongyi,xiaoxi_addFrend;
    private XListView tongxun_xlv;
    private List<String> usernames;
    private LIebiaoAdapter ada;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_tongxunlu,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xiaoxi_tongyi=view.findViewById(R.id.xiaoxi_tongyi);
        tongxun_xlv=view.findViewById(R.id.tongxun_xlv);
        xiaoxi_addFrend=view.findViewById(R.id.xiaoxi_addFrend);
        //同意
        xiaoxi_tongyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), NewFrendActivity.class));
            }
        });

        //我要添加的人
        xiaoxi_addFrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyAddFrendActivity.class));
            }
        });

        tongxun_xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(getActivity(), CozeActivity.class);
                intent.putExtra("userllla",usernames.get(position-1));
                startActivity(intent);

            }
        });

        initData();
    }

    public void shuxinLie(){
        if(ada==null){
            ada = new LIebiaoAdapter(usernames,getActivity());
            tongxun_xlv.setAdapter(ada);
        }else {
            ada.notifyDataSetChanged();
        }
    }

    /**
     * 获取好友
     */
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {

        try {
            //获取好友列表
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(usernames.size()!=0){
                                    shuxinLie();
                                }else {
                                    Toast.makeText(getActivity(),"没有好友啊~",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
