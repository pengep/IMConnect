package test.bick.com.imconnect.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import test.bick.com.imconnect.CozeActivity;
import test.bick.com.imconnect.R;
import view.xlistview.XListView;

/**
 * User:白二鹏
 * Created by Administrator-12-20 18 : 46
 */

@SuppressLint("ValidFragment")
public class FragmentXiaoxiLie extends Fragment {

    private View view;
    private XListView xlvaaa;

    private String userName;
//
//    public FragmentXiaoxiLie(String userName) {
//       this. userName=userName;
//        System.out.println("cccccccccccccc"+userName);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_xiaoxilie,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlvaaa=view.findViewById(R.id.xlvaaa);

        initData();
    }

    private void initData() {


        xlvaaa.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView=new TextView(getActivity());
                textView.setText("发起聊天！");
                return textView;
            }
        });
        xlvaaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=  new Intent(getActivity(), CozeActivity.class);
                in.putExtra("userName",userName);
                startActivity(in);
            }
        });
    }

}
