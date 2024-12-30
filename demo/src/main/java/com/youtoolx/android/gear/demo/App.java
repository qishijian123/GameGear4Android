package com.youtoolx.android.gear.demo;

import android.app.Application;

import com.youtoolx.android.gear.sdk.GearSDK;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //同意隐私协议
        GearSDK.setAgreePrivacy(true);
    }
}