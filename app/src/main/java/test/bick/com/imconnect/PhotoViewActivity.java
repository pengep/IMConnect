package test.bick.com.imconnect;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import test.bick.com.imconnect.bean.MyBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoViewActivity extends AppCompatActivity {

    private ViewPager vp_vp_photo;
    private ArrayList<MyBean> photoList;
    private ArrayList<MyBean> myList;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        vp_vp_photo=findViewById(R.id.vp_vp_photo);

        myList=new ArrayList<>();
        photoList = getIntent().getParcelableArrayListExtra("photoList");
        index = getIntent().getIntExtra("index",-1);
        myList.addAll(photoList);
        initData();

    }

    private void initData() {

        MyAdapter ada=new MyAdapter();
        vp_vp_photo.setAdapter(ada);
        vp_vp_photo.setCurrentItem(index);
    }
    PhotoViewAttacher mAttacher;
    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View vew=View.inflate(PhotoViewActivity.this,R.layout.layout_photoview,null);
            PhotoView photoView = vew.findViewById(R.id.my_iv_photo);

//            // 启用图片缩放功能
//            photoView.enable();
//// 禁用图片缩放功能 (默认为禁用，会跟普通的 ImageView 一样，缩放功能需手动调用 enable()启用)
//            photoView.disenable();
//// 获取图片信息
//            Info info = photoView.getInfo();
//// 从普通的 ImageView 中获取 Info
//// 从一张图片信息变化到现在的图片，用于图片点击后放大浏览，具体使用可以参照 demo 的使用
//            photoView.animaFrom(info);
//// 从现在的图片变化到所给定的图片信息，用于图片放大后点击缩小到原来的位置，具体使用可以参照 demo 的使用
//            photoView.animaTo(info,new Runnable() {
//                @Override
//                public void run() {
//                    //动画完成监听
//                }
//            });
//// 获取/设置 动画持续时间
//            photoView.setAnimaDuring(  300);
//            int d = photoView.getAnimaDuring();
//// 获取/设置 最大缩放倍数
//            photoView.setMaxScale(5);
//            float maxScale = photoView.getMaxScale();
            mAttacher = new PhotoViewAttacher(photoView);
            if(myList.get(position).getP2Type()==100){ //本地路径
               // photoView.setImageURI(Uri.parse(myList.get(position).getP1()));
                photoView.setImageBitmap(BitmapFactory.decodeFile(myList.get(position).getP1()));
                mAttacher.update();
            }else {
                Glide.with(PhotoViewActivity.this).load(myList.get(position).getP1()).into(photoView);
            }
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            container.addView(vew);
            return vew;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myList=null;
    }
}
