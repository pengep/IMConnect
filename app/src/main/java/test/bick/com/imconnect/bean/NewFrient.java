package test.bick.com.imconnect.bean;

/**
 * User:白二鹏
 * Created by Administrator-12-21 11 : 02
 */

public class NewFrient {
    private String name;
    private String reason; //添加理由

    public NewFrient(String name, String reason) {
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
