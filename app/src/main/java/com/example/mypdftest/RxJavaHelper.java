package com.example.mypdftest;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaHelper {
    public static @NonNull Observable<Object> processDataStream() {
        return Observable.create(emitter -> {
                    // 执行异步操作，例如网络请求、数据库查询等
                    // 模拟异步操作结果，这里使用一个简单的延时
                    Thread.sleep(2000);

                    // 模拟异步操作成功或失败的条件
                    boolean success = Math.random() < 0.5;

                    // 发射异步操作结果
                    emitter.onNext(success);
                    emitter.onComplete();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<String> openRxjavaData(){
      return   Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("123456aabbcc");
                emitter.onComplete();
            }
        }).map(data -> {
            // 对数据进行处理或转换
            return data.toUpperCase();
        }).delay(5000, TimeUnit.MILLISECONDS);
    }
}
