package com.het.hetsharesdkdemo.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.het.hetsharesdkdemo.MainActivity;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by sunny on 2016/1/11.
 * Annotion:
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private String WeiXinAppID = MainActivity.mWeixinAppId;
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WeiXinAppID, true);
        api.registerApp(WeiXinAppID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.e("platform",resp.getType()+"");
        String result = "";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "发送取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }

        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        this.finish();
    }
}
