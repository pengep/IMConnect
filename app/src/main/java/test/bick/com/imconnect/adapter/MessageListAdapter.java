package test.bick.com.imconnect.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import test.bick.com.imconnect.R;
import test.bick.com.imconnect.bean.MyBean;

/**
 * User:白二鹏 消息列表
 * Created by Administrator-12-21 09 : 44
 */

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MyBean> list;
    private Context context;
    private LayoutInflater inflater;
    public MessageListAdapter(List<MyBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 返回视图
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        RecyclerView.ViewHolder holder=null;

        if(viewType==1||viewType==66){//接收
            view = inflater.inflate(R.layout.mes_recitem_accept,null);
            holder=new ReceiveItemViewHolder(view);
        }else if(viewType==0||viewType==55){
            view=inflater.inflate(R.layout.mes_recitem_send,null);
            holder=new SendItemViewHolder(view);
        }else if(viewType==44){//接收
            view = inflater.inflate(R.layout.item_yuyin_accept,null);
            holder=new ReceiveYuyinViewHolder(view);

        }else if(viewType==33){
            view = inflater.inflate(R.layout.item_yuyin_send,null);
            holder=new SendYuyinViewHolder(view);
        }
        return holder;
    }

    /**
     * 处理数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int itemViewType = getItemViewType(position);

        if (itemViewType==1) {
            ReceiveItemViewHolder holder1= (ReceiveItemViewHolder) holder;
            holder1.receTv.setText(list.get(position).getMessage());
        }
        else if(itemViewType==0){
            SendItemViewHolder holder2= (SendItemViewHolder) holder;
            holder2.sendTv.setText(list.get(position).getMessage());
        }
        else if(itemViewType==55){//发送
            SendItemViewHolder holder2= (SendItemViewHolder) holder;
            holder2.iv.setVisibility(View.VISIBLE);
            holder2.sendTv.setVisibility(View.GONE);
            holder2.iv.setImageURI(Uri.parse(list.get(position).getP1()));
            holder2.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageLinsenter.sendImage(list.get(position).getP1(),position);
                }
            });

        }
        else if(itemViewType==66){//接收
            final ReceiveItemViewHolder holder1= (ReceiveItemViewHolder) holder;
            holder1.receTv.setVisibility(View.GONE);
            holder1.mes_item_img.setVisibility(View.VISIBLE);

           Glide.with(context).load(list.get(position).getP1()).into(holder1.mes_item_img);


            holder1.mes_item_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageLinsenter.receiverImage(list.get(position).getP1(),position);
                }
            });

        }
        else if(itemViewType==44) { //接收
            ReceiveYuyinViewHolder holder1 = (ReceiveYuyinViewHolder) holder;

            //毫秒数转换为 date
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            date.setTime(list.get(position).getLength());

            holder1.mesyuyin_accept_item_tv_time.setText(simpleDateFormat.format(date)); //接收长度
            holder1.mesyuyin_accept_item_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linsenter.receiverVoice(list.get(position).getY1());
                }
            });
        }
        else if(itemViewType==33) { //发送
            SendYuyinViewHolder holder1 = (SendYuyinViewHolder) holder;
            holder1.messend_item_tv_time.setText(list.get(position).getY1()+"");
            holder1.messend_item_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    linsenter.sendVoice(list.get(position).getPathYY());
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getPanduan() == 0) {
            return 0; //发送
        } else if (list.get(position).getPanduan() == 1) {
            return 1; //接收
        } else if (list.get(position).getPanduan() == 33) {
            //33 发送语音
            return 33;
        } else if (list.get(position).getPanduan() == 44) {
            //44 接收语音
            return 44;
        } else if (list.get(position).getPanduan() == 55) {
            return 55; //发送
        } else if (list.get(position).getPanduan() == 66) {
            return 66;//接收
        }else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ReceiveItemViewHolder extends RecyclerView.ViewHolder {

        private  TextView receTv;
        private ImageView mes_item_img;
        private LinearLayout mes_accept_rll;
        public ReceiveItemViewHolder(View itemView) {
            super(itemView);
            receTv=itemView.findViewById(R.id.mes_item_tvmes);
            mes_item_img=itemView.findViewById(R.id.mes_item_img);
            mes_accept_rll=itemView.findViewById(R.id.mes_accept_rll);
        }
    }
    public class SendItemViewHolder extends RecyclerView.ViewHolder {

        private  TextView sendTv;
        private ImageView iv;
        public SendItemViewHolder(View itemView) {
            super(itemView);
            sendTv=itemView.findViewById(R.id.mesf_item_tvmes);
            iv=itemView.findViewById(R.id.mesf_item_imgMes);
        }
    }

    /**
     * 发送 语音
     */
    public class SendYuyinViewHolder extends RecyclerView.ViewHolder {

        private  TextView messend_item_tv,messend_item_tv_time;
        public SendYuyinViewHolder(View itemView) {
            super(itemView);
            messend_item_tv=itemView.findViewById(R.id.messend_item_tv);
            messend_item_tv_time=itemView.findViewById(R.id.messend_item_tv_time);
        }
    }
    public class ReceiveYuyinViewHolder extends RecyclerView.ViewHolder {

        private  TextView mesyuyin_accept_item_tv,mesyuyin_accept_item_tv_time;

        public ReceiveYuyinViewHolder(View itemView) {
            super(itemView);
            mesyuyin_accept_item_tv=itemView.findViewById(R.id.mesyuyin_accept_item_tv);
            mesyuyin_accept_item_tv_time=itemView.findViewById(R.id.mesyuyin_accept_item_tv_time);
        }
    }
    private VoiceLinsenter linsenter;
    public void setLinsenter(VoiceLinsenter linsenter){
        this.linsenter=linsenter;
    }
    public interface VoiceLinsenter{
        void sendVoice(String url);
        void receiverVoice(String url);
    }

    private ImageLinsenter imageLinsenter;
    public void setLinsenter(ImageLinsenter imageLinsenter){
        this.imageLinsenter=imageLinsenter;
    }
    public interface ImageLinsenter{
        void sendImage(String url,int index);
        void receiverImage(String url,int index);
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
}
