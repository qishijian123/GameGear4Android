package com.youtoolx.android.gear.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.youtoolx.android.gear.sdk.GearSDK;

public class MainActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {

    private WebView mWebView;
    private TextView mTvIndex;
    private Button mBtnShowView;
    private Button mBtnHiddenView;
    private Button mBtnControl;
    private SeekBar mSeekBar;
    private RadioGroup mRgMode;
    private RadioButton mRbUp;
    private long mBackPressedTime;
    private boolean mIsStart;
    private int mSpeedIndexUp;
    private int mSpeedIndexDown;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.webview);
        mTvIndex = findViewById(R.id.tv_index);
        mRgMode = findViewById(R.id.rg_mode);
        mRbUp = findViewById(R.id.rb_up);
        mSeekBar = findViewById(R.id.seekbar);
        mBtnShowView = findViewById(R.id.btn_show_view);
        mBtnHiddenView = findViewById(R.id.btn_hidden_view);
        mBtnControl = findViewById(R.id.btn_control);
        mBtnShowView.setOnClickListener(this);
        mBtnHiddenView.setOnClickListener(this);
        mBtnControl.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mRgMode.setOnCheckedChangeListener(this);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new Object(), "test");
        mWebView.loadUrl("file:///android_asset/h5game.html");

        //默认加速模式
        mRbUp.setChecked(true);

        //默认速度档位
        mSpeedIndexUp = 1;
        mSpeedIndexDown = 1;

        //最大速度档位
        int maxIndex = 15;
        //最大真实加速倍速
        int maxGear = 5;

        mSeekBar.setMin(1);
        mSeekBar.setMax(maxIndex);
        mSeekBar.setProgress(isUpMode() ? mSpeedIndexUp : mSpeedIndexDown);

        //配置速度档位
        GearSDK.config(maxIndex, maxGear);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_view:
                Window window = getWindow();
                int heightPixels = window.getDecorView().getHeight();
                //显示悬浮球
                GearSDK.showGearView(MainActivity.this, 0, heightPixels / 2);
                break;
            case R.id.btn_hidden_view:
                //隐藏悬浮球
                GearSDK.hideGearView();
                break;
            case R.id.btn_control:
                mIsStart = !mIsStart;
                if (mIsStart) {
                    mBtnControl.setText(isUpMode() ? "停止加速" : "停止减速");
                    //开启
                    GearSDK.start();
                } else {
                    mBtnControl.setText(isUpMode() ? "开启加速" : "开启减速");
                    //停止
                    GearSDK.stop();
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setSpeedIndex(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mSpeedIndexUp != mSpeedIndexDown) {
            if (isUpMode()) {
                mSeekBar.setProgress(mSpeedIndexUp);
            } else {
                mSeekBar.setProgress(mSpeedIndexDown);
            }
        } else {
            setSpeedIndex(mSpeedIndexUp);
        }
    }

    private void setSpeedIndex(int speedIndex) {
        if (isUpMode()) {
            //加速模式
            mSpeedIndexUp = speedIndex;
            mTvIndex.setText("加速×" + mSpeedIndexUp);
            mBtnControl.setText(mIsStart ? "停止加速" : "开启加速");
            GearSDK.speedUp(speedIndex);
        } else {
            //减速模式
            mSpeedIndexDown = speedIndex;
            mTvIndex.setText("减速×" + mSpeedIndexDown);
            mBtnControl.setText(mIsStart ? "停止减速" : "开启减速");
            GearSDK.speedDown(speedIndex);
        }
    }

    private boolean isUpMode() {
        return mRbUp.isChecked();
    }

    @Override
    public void onBackPressed() {
        long currentTime = SystemClock.elapsedRealtime();
        if (currentTime - mBackPressedTime <= 2000) {
            super.onBackPressed();
            System.exit(0);
        } else {
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            mBackPressedTime = currentTime;
        }
    }
}