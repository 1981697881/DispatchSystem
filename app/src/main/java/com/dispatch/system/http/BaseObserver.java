package com.dispatch.system.http;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.dispatch.system.BuildConfig;
import com.dispatch.system.entity.BaseBean;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 处理网络请求码
 *
 * @author chenjunxu
 * @date 2017/9/27
 */
public abstract class BaseObserver<T> implements Observer<T> {
    private Disposable d;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d;
        getDisposable(d);
    }

    @Override
    public void onNext(T t) {
        try {
            if (t instanceof BaseBean) {
                int status = ((BaseBean) t).getStatus();
                if (status != 200) {
                    ToastUtils.showLong(((BaseBean) t).getDesc());
                    onError(new MyException(status));
                } else {
                    onSuccess(t);
                }
            }
            if (d != null && !d.isDisposed()) {
                d.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        onCancel();
        Log.e("PocketPiano", "MyObserver ==> e.getMessage() ==> " + e.getMessage());
        Log.e("PocketPiano", "MyObserver ==> e.getStackTrace() ==> " + e.getStackTrace());
        Log.e("PocketPiano", "MyObserver ==> e.toString() ==> " + e.toString());

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code == 404 || code == 500) {
                onError("服务器异常，请稍后重试");
                return;
            } else if(code == 502 || code == 504) {
                onError("服务器繁忙，请稍后重试");
            }
            return;
        } else if (e instanceof SocketTimeoutException) {
            onError("请求超时");
            return;
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
            onError("网络异常");
            return;
        } else if (e instanceof ClassCastException) {
            if (BuildConfig.DEBUG) {
                onError("类型转换错误");
            }
        } else if (e instanceof MyException) {
            onError(String.valueOf(((MyException)e).getCode()));
        } else {
            onError("");
        }
    }

    @Override
    public void onComplete() {
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        onCancel();
    }

    protected abstract void getDisposable(Disposable d);

    /**
     * 用户关闭刷新加载，progressbar 等控件
     */
    protected void onCancel() {
    }

    protected abstract void onSuccess(T t);

    /**
     * 显示错误信息
     */
    protected abstract void onError(String msg);
}
