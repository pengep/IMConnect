package test.bick.com.imconnect.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * User:白二鹏
 * Created by Administrator-12-21 09 : 58
 */

public class MyBean implements Parcelable {
    private String message,y1,p1,v1;
    private int panduan;

    private int  y2Type;//语音
    private String pathYY;//路径
    private int length;

    private int p2Type;
    private int  v2Type;

    public MyBean() {
    }

    protected MyBean(Parcel in) {
        message = in.readString();
        y1 = in.readString();
        p1 = in.readString();
        v1 = in.readString();
        panduan = in.readInt();
        y2Type = in.readInt();
        pathYY = in.readString();
        length = in.readInt();
        p2Type = in.readInt();
        v2Type = in.readInt();
    }

    public static final Creator<MyBean> CREATOR = new Creator<MyBean>() {
        @Override
        public MyBean createFromParcel(Parcel in) {
            return new MyBean(in);
        }

        @Override
        public MyBean[] newArray(int size) {
            return new MyBean[size];
        }
    };

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getPathYY() {
        return pathYY;
    }

    public void setPathYY(String pathYY) {
        this.pathYY = pathYY;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public int getPanduan() {
        return panduan;
    }

    public void setPanduan(int panduan) {
        this.panduan = panduan;
    }

    public int getY2Type() {
        return y2Type;
    }

    public void setY2Type(int y2Type) {
        this.y2Type = y2Type;
    }

    public int getP2Type() {
        return p2Type;
    }

    public void setP2Type(int p2Type) {
        this.p2Type = p2Type;
    }

    public int getV2Type() {
        return v2Type;
    }

    public void setV2Type(int v2Type) {
        this.v2Type = v2Type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeString(y1);
        dest.writeString(p1);
        dest.writeString(v1);
        dest.writeInt(panduan);
        dest.writeInt(y2Type);
        dest.writeString(pathYY);
        dest.writeInt(length);
        dest.writeInt(p2Type);
        dest.writeInt(v2Type);
    }
}
