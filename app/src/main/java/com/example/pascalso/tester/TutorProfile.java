package com.example.pascalso.tester;

/**
 * Created by owner on 8/30/15.
 */
public class TutorProfile {
    private String mName;
    private String mDes;
    private int mThumbnail;

    public String getName(){
        return mName;
    }

    public void setName(String name){
        this.mName = name;
    }

    public String getDes(){
        return mDes;
    }

    public void setDes(String des){
        this.mDes = des;
    }

    public int getThumbnail(){
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail){
        this.mThumbnail = thumbnail;
    }
}
