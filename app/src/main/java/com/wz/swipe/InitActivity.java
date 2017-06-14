package com.wz.swipe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ysepay.mobileportal.pub.util.StringUtils;
import com.ysepay.mobileswipcard.enter.YstSwipeIntent;
import com.ysepay.mobileswipcard.impl.SwipInitInterface;
import com.ysepay.mobileswipcard.impl.YSTranCallback;
import com.ysepay.mobileswipcard.vo.SwipeCardResultBean;

import java.util.ArrayList;
import java.util.List;

public class InitActivity extends AppCompatActivity implements SwipInitInterface,YSTranCallback {
    private static final String TAG = "InitActivity";

    private String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private int permissionsRequestCode = 0xff32;

    private String mercId;
    private String key;
    private String bbCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        Intent intent = getIntent();
        SwipeParam swipeParam = intent.getParcelableExtra("param");
        mercId = swipeParam.getMercId();
        bbCode = swipeParam.getBbCode();
        key = swipeParam.getKey();
        Log.i(TAG,"mercId:"+mercId+" bbCode:"+bbCode+" key:"+key);
        if (requestPermission()){
            initSwipe();
        }
    }

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return true;
        }
        List<String> requestPermissions = new ArrayList<>();
        for (String permission : permissions){
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)){
                requestPermissions.add(permission);
            }
        }
        int size = requestPermissions.size();
        if (size > 0) {
            ActivityCompat.requestPermissions(this, requestPermissions.toArray(new String[size]), permissionsRequestCode);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void initSwipe() {
//        mercId = "YST15121411334512345678900987652"; //  生产环境
//        key = "YSDFHSKDFSSD";
        YstSwipeIntent.get().initSwipSDK(this, bbCode, mercId, key, this, false);//ture表示测试环境
    }

    private boolean checkAmount(String amount) {
        if (StringUtils.isEmpty(amount)) {
            return false;
        }
        if (amount.length() > 11){
            return false;
        }
        if (!StringUtils.isNumeric(amount)) {
            return false;
        }
        if (Long.parseLong(amount) <= 0) {
            return false;
        }
        return true;
    }

    private void intentPosCard(String amountStr, String orderIdStr, String remarkStr) {
        if (checkAmount(amountStr)) {
            YstSwipeIntent.get().startSwipSDK(this, amountStr, orderIdStr, remarkStr, this);
            return;
        }
        Toast.makeText(this, "请输入正确的金额", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitFinish(String code) {
        Log.i(TAG,"code:"+code);
        if (SwipInitInterface.CODE_SUC.equals(code)) {
            Toast.makeText(this, "初始化成功", Toast.LENGTH_LONG).show();
            intentPosCard("1",System.currentTimeMillis()+"","支付");
        } else {
            Toast.makeText(this, "初始化失败，错误码为：" + code, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void tranCallback(String code, String description, String orderId, String remark,SwipeCardResultBean resultBean) {
        String content = "";
        SwipeResult result = new SwipeResult();
        result.setCode(code);
        result.setOrderId(orderId);
        result.setRemark(remark);
        result.setDescription(description);
        sendBroadcast(new Intent("filter_swipe").putExtra("result",result));
        finish();
        if (code.equals("00")) {
            content = "交易成功:" + description + ",来自订单号：" + orderId + "," + remark+resultBean.getOrderId();
            Log.i("tranCallback", "交易成功:" + description + ",来自订单号：" + orderId + "," + remark);
        } else if (code.equals("01")) {
            content = "交易失败:" + description + ",来自订单号：" + orderId + "," + remark;
            Log.i("tranCallback", "交易失败:" + description + ",来自订单号：" + orderId + "," + remark);
        } else if (code.equals("02")) {
            content = "交易超时:" + description + ",来自订单号：" + orderId + "," + remark;
            Log.i("tranCallback", "交易超时:" + description + ",来自订单号：" + orderId + "," + remark);
        } else if (code.equals("03")) {
            content = "交易取消:" + description + ",来自订单号：" + orderId + "," + remark;
            Log.i("tranCallback", "交易取消:" + description + ",来自订单号：" + orderId + "," + remark);
        }
    }

    @Override
    public void tranQryCallback(String busiCode,String busiDesc,String ystMercId, String orderId,String referenceNum, String tranDate, String transAmt,String stateCode, String stateDesc) {
        if("00".equals(busiCode)){
            String result = "订单查询返回的数据：" + "商户号：" + ystMercId + "，订单号："
                    + orderId + "，参考号：" + referenceNum + "，交易日期："
                    + tranDate + "，交易金额：" + transAmt + "，状态码："
                    + stateCode + "，状态描述：" + stateDesc;
            Log.i("tranQryCallback", result);
        }else{
            Toast.makeText(this, "获取失败:"+busiDesc, Toast.LENGTH_LONG).show();
        }
    }
}
