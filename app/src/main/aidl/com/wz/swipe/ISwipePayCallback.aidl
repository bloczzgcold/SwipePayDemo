// ISwipePayCallback.aidl
package com.wz.swipe;

import com.wz.swipe.SwipeResult;

// Declare any non-default types here with import statements

interface ISwipePayCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onPayResult(inout SwipeResult result);
}
