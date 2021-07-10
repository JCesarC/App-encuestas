package com.develop.appprotov1.io.response;
//-----------------------------------com.example.Post.java-----------------------------------

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("pre1")
    @Expose
    private String pre1;
    @SerializedName("pre2")
    @Expose
    private String pre2;
    @SerializedName("pre3")
    @Expose
    private String pre3;
    @SerializedName("pre4")
    @Expose
    private String pre4;
    @SerializedName("pre5")
    @Expose
    private String pre5;
    @SerializedName("pre6")
    @Expose
    private String pre6;
    @SerializedName("pre7")
    @Expose
    private String pre7;
    @SerializedName("pre8")
    @Expose
    private String pre8;
    @SerializedName("pre9")
    @Expose
    private String pre9;
    @SerializedName("pre10")
    @Expose
    private String pre10;

    public String getPre1() {
        return pre1;
    }

    public void setPre1(String pre1) {
        this.pre1 = pre1;
    }

    public String getPre2() {
        return pre2;
    }

    public void setPre2(String pre2) {
        this.pre2 = pre2;
    }

    public String getPre3() {
        return pre3;
    }

    public void setPre3(String pre3) {
        this.pre3 = pre3;
    }

    public String getPre4() {
        return pre4;
    }

    public void setPre4(String pre4) {
        this.pre4 = pre4;
    }

    public String getPre5() {
        return pre5;
    }

    public void setPre5(String pre5) {
        this.pre5 = pre5;
    }

    public String getPre6() {
        return pre6;
    }

    public void setPre6(String pre6) {
        this.pre6 = pre6;
    }

    public String getPre7() {
        return pre7;
    }

    public void setPre7(String pre7) {
        this.pre7 = pre7;
    }

    public String getPre8() {
        return pre8;
    }

    public void setPre8(String pre8) {
        this.pre8 = pre8;
    }

    public String getPre9() {
        return pre9;
    }

    public void setPre9(String pre9) {
        this.pre9 = pre9;
    }

    public String getPre10() {
        return pre10;
    }

    public void setPre10(String pre10) {
        this.pre10 = pre10;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pre1='" + pre1 + '\'' +
                ", pre2='" + pre2 + '\'' +
                ", pre3='" + pre3 + '\'' +
                ", pre4='" + pre4 + '\'' +
                ", pre5='" + pre5 + '\'' +
                ", pre6='" + pre6 + '\'' +
                ", pre7='" + pre7 + '\'' +
                ", pre8='" + pre8 + '\'' +
                ", pre9='" + pre9 + '\'' +
                ", pre10='" + pre10 + '\'' +
                '}';
    }
}
