package com.youtoolx.android.gear.demo;

import android.app.Application;

import com.youtoolx.android.gear.sdk.GearSDK;

// 自定义Application
public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //同意隐私协议
        GearSDK.setAgreePrivacy(true);
    }
}