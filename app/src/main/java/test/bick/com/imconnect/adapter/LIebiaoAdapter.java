package test.bick.com.imconnect.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import test.bick.com.imconnect.R;

/**
 * User:白二鹏
 * Created by Administrator-12-20 21 : 27
 */

public class LIebiaoAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public LIebiaoAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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

            convertView=View.inflate(context, R.layout.item_liebiao_xlv,null);
            TextView tv = convertView.findViewById(R.id.tv_item_xlv);
            tv.setText(list.get(position));
        }
        return convertView;
    }
}
