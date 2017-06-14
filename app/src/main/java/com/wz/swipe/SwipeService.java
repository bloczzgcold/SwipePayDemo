package com.wz.swipe;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhangzhenguo on 2017/6/13.
 * 刷卡服务
 */

public class SwipeService extends Service {
    private static final String TAG = "SwipeService";

    private ISwipePayCallback mSwipePayCallback;
    private SwipeReceiver mSwipeReceiver;


    public class SwipeServiceImpl extends ISwipeAidlInterface.Stub{

        @Override
        public void payResult(SwipeParam swipeParam, ISwipePayCallback callback) throws RemoteException {
            Log.i(TAG,"绑定成功");
            mSwipePayCallback = callback;
            Intent intent = new Intent(SwipeService.this,InitActivity.class);
            intent.putExtra("param",swipeParam);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSwipeReceiver = new SwipeReceiver();
        registerReceiver(mSwipeReceiver,new IntentFilter("filter_swipe"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new SwipeServiceImpl();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSwipeReceiver != null){
            unregisterReceiver(mSwipeReceiver);
        }
    }

    class SwipeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            SwipeResult swipeResult = intent.getParcelableExtra("result");
            try {
                mSwipePayCallback.onPayResult(swipeResult);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
