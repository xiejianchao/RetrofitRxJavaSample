package com.github.sample.retrofitrxjavasample.net.download;

/**
 * Created by Cmad on 2016/4/28.
 */
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

import com.github.sample.retrofitrxjavasample.model.ProgressBean;
import com.github.sample.retrofitrxjavasample.net.progress.DownloadProgressHandler;
import com.orhanobut.logger.Logger;

/**
 * 包装的响体，处理进度
 */
public class ProgressResponseBody extends ResponseBody {
    private static final String TAG = ProgressResponseBody.class.getSimpleName();
    //实际的待包装响应体
    private final ResponseBody responseBody;
    //进度回调接口
    private ProgressResponseListener progressListener;
    //包装完成的BufferedSource
    private BufferedSource bufferedSource;

    private MyHandler myHandler;

    private DownloadProgressHandler downloadProgressHandler;

    /**
     * 构造函数，赋值
     *
     * @param responseBody     待包装的响应体
     * @param progressListener 回调接口
     */
    public ProgressResponseBody(ResponseBody responseBody, ProgressResponseListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;

        myHandler = new MyHandler(Looper.getMainLooper());
    }
    /**
     * 构造函数，赋值
     *  @param responseBody     待包装的响应体
     * @param downloadProgressHandler 回调接口
     */
    public ProgressResponseBody(ResponseBody responseBody, DownloadProgressHandler downloadProgressHandler) {
        this.responseBody = responseBody;
        this.downloadProgressHandler = downloadProgressHandler;
    }


    /**
     * 重写调用实际的响应体的contentType
     *
     * @return MediaType
     */
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    /**
     * 重写调用实际的响应体的contentLength
     *
     * @return contentLength
     * @throws IOException 异常
     */
    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    /**
     * 重写进行包装source
     *
     * @return BufferedSource
     */
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            //包装
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 读取，回调进度接口
     *
     * @param source Source
     * @return Source
     */
    private Source source(Source source) {

        return new ForwardingSource(source) {
            //当前读取字节数
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);



                //增加当前读取的字节数，如果读取完成了bytesRead会返回-1
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                //回调，如果contentLength()不知道长度，会返回-1
                if(progressListener != null){
                    progressListener.onResponseProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                }


//                Log.e(TAG,"totalBytesRead:" + totalBytesRead);
//                Log.e(TAG,"responseBody.contentLength():" + responseBody.contentLength());
//                Log.e(TAG,"downloadProgressHandler:" + downloadProgressHandler);

                if (downloadProgressHandler != null) {
                    ProgressBean progressBean = new ProgressBean();
                    progressBean.setBytesRead(totalBytesRead);
                    progressBean.setContentLength(responseBody.contentLength());
                    boolean done = bytesRead == -1;
                    progressBean.setDone(done);
                    downloadProgressHandler.sendMessage(progressBean);
                }


                return bytesRead;
            }
        };
    }

    private static class MyHandler extends Handler {
        /**
         * Use the provided {@link Looper} instead of the default one.
         *
         * @param looper The looper, must not be null.
         */
        public MyHandler(Looper looper) {
            super(looper);
        }
    }

}