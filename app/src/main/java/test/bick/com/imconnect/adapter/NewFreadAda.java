package test.bick.com.imconnect.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import test.bick.com.imconnect.R;
import test.bick.com.imconnect.bean.NewFrient;

/**
 * User:白二鹏
 * Created by Administrator-12-27 15 : 14
 */

public class NewFreadAda extends BaseAdapter {
    private Context context;
    private List<NewFrient> list;

    public NewFreadAda(Context context, List<NewFrient> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=View.inflate(context, R.layout.new_fradd_item,null);
            final TextView tvv_name=convertView.findViewById(R.id.nnnnew_name);
            TextView tv_mes=convertView.findViewById(R.id.nnnnew_message);

            Button btn_tongyi=convertView.findViewById(R.id.nnnew_btn_tongyi);
            final Button btn_jujue=convertView.findViewById(R.id.nnnew_btn_tongyi);

            /**
             * 同意
             */
            btn_tongyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(tvv_name.getText().toString().trim());
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
            /**
             * 拒绝
             */
            btn_jujue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(tvv_name.getText().toString().trim());
                        btn_jujue.setText("已经拒绝，不能点击");
                        btn_jujue.setClickable(false);
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });


            tvv_name.setText(list.get(position).getName());
            tv_mes.setText(list.get(position).getReason());
        }

        return convertView;
    }
}
