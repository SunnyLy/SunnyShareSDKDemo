package com.het.hetsharesdkdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.het.share.dialog.CommonShareDialog;
import com.het.share.listener.ICommonShareLinstener;
import com.het.share.manager.CommonShareManager;
import com.het.share.manager.CommonSharePlatform;
import com.het.share.model.CommonShareBean;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class MainActivity extends AppCompatActivity implements ICommonShareLinstener, IUiListener, View.OnClickListener {

    private CommonShareManager mShareManger;
    private CommonShareDialog mShareDialog;

    //private String musicUrl = "http://music.baidu.com/song/256006577";
    private String musicUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
    private String musicDataUrl = "http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3";
    private String mTargetUrl = "http://www.baidu.com";
    private String mTitle = "Sunny";
    private String mDescription = "Hello,I'm Sunny";
    private String mImgUrl = "http://pic15.nipic.com/20110725/7067632_020203324179_2.jpg";

    private String mWeixinAppId = "wxd930ea5d5a258f4f";//微信Demo用
    private String mWeixinSecrect = "";//分享的时候不要用到secretId,只有微信登录的时候要

    private String mQQAppId = "1104541762";//CLife用的
    private String mSinaWeiboAppId = "3475229326";//Clife用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.start_share);
        btnStart.setOnClickListener(this);
        initParams();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initParams() {
        mShareManger = new CommonShareManager.Builder(this).
                registerWeixin(mWeixinAppId).
                registerQQ(mQQAppId).
                registerSinaWeibo(mSinaWeiboAppId).create();
        mShareDialog = new CommonShareDialog(this, this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartShare(CommonSharePlatform sharePlatform) {

        CommonShareBean shareBean = new CommonShareBean();
        shareBean.setUiListener(this);
        shareBean.setTitle(mTitle);
        shareBean.setDescription(mDescription);
        shareBean.setAppName("SunnyDemo");
        shareBean.setTargetUrl(mTargetUrl);
        shareBean.setMusicUrl(musicUrl);
        shareBean.setMusicDataUrl(musicDataUrl);
        shareBean.setBm(null);
        shareBean.setSharePlatform(sharePlatform);

        //分享音乐
        // mShareManger.shareMusic(shareBean);
        //分享网页
        shareBean.setImgUrl(mImgUrl);
        mShareManger.shareWebpage(shareBean);
        Toast.makeText(this,"点击："+sharePlatform,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareSuccess(CommonSharePlatform sharePlatform, String msg) {

        Toast.makeText(this,"分享成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareFialure(CommonSharePlatform sharePlatform, String msg) {
        Toast.makeText(this,"分享失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShareCancel(CommonSharePlatform sharePlatform) {
        Toast.makeText(this,"取消分享",Toast.LENGTH_SHORT).show();
    }

    /**
     * //////////////下面是腾讯分享回调
     * @param o
     */
    @Override
    public void onComplete(Object o) {
        Toast.makeText(this,"QQ分享完成",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(UiError uiError) {
        Toast.makeText(this,"QQ分享onError",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel() {
        Toast.makeText(this,"QQ分享onCancel",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        startShare(v);
    }

    public void startShare(View view) {

        if (mShareDialog != null && !mShareDialog.isShowing()) {
            mShareDialog.show();
        }
    }
}
