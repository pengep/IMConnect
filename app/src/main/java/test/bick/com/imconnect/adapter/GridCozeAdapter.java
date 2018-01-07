package test.bick.com.imconnect.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.bick.com.imconnect.R;
import test.bick.com.imconnect.bean.XiaoxiPlusBean;

/**
 * User:白二鹏
 * Created by Administrator-12-29 14 : 54
 */

public class GridCozeAdapter extends BaseAdapter {

    private List<XiaoxiPlusBean> listVp;
    private Context context;

    public GridCozeAdapter(List<XiaoxiPlusBean> listVp, Context context) {
        this.listVp = listVp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listVp.size()-1;
    }

    @Override
    public Object getItem(int position) {
        return listVp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_vp1_xiaoxi,null);
            ImageView iv= convertView.findViewById(R.id.item_vi1_iv);
            TextView tv= convertView.findViewById(R.id.item_vi1_tv);

            iv.setImageResource(listVp.get(position).iv);
            tv.setText(listVp.get(position).name);
        }
        return convertView;
    }
}
