package com.example.mypdftest;

import android.annotation.SuppressLint;
import android.util.Log;

public class TestFunction {
    private static final String TAG = "TestFunction";
    @SuppressLint("CheckResult")
    public static void main(String[] args) {
        RxJavaHelper.processDataStream()
                .subscribe(
                        result -> {
                            // 处理异步操作的结果
                            //Log.i(TAG,"异步拿到的结果是？"+result);
                            System.out.println("异步拿到的结果是？"+result);
                        },
                        throwable -> {
                            // 处理错误情况
                        },
                        () -> {
                            // 最后的逻辑，当Observable完成时调用
                        }
                );

    }

}
