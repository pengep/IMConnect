package test.bick.com.imconnect.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import test.bick.com.imconnect.bean.NewFrient;

/**
 * User:白二鹏
 * Created by Administrator-12-21 11 : 05
 */

public class NewFridAdapter extends BaseAdapter {
    private Context context;
    private List<NewFrient> list;

    public NewFridAdapter(Context context, List<NewFrient> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
