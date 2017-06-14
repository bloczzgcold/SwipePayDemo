// ISwipeAidlInterface.aidl
package com.wz.swipe;

import com.wz.swipe.SwipeParam;
import com.wz.swipe.ISwipePayCallback;

// Declare any non-default types here with import statements

interface ISwipeAidlInterface {

    void payResult(in SwipeParam swipeParam,ISwipePayCallback callback);
}
