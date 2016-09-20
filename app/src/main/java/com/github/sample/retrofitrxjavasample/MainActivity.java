package com.github.sample.retrofitrxjavasample;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.github.sample.retrofitrxjavasample.api.DownloadService;
import com.github.sample.retrofitrxjavasample.model.GankBeauty;
import com.github.sample.retrofitrxjavasample.model.MovieModel;
import com.github.sample.retrofitrxjavasample.model.TwoResultModel;
import com.github.sample.retrofitrxjavasample.net.ApiManager;
import com.github.sample.retrofitrxjavasample.net.HttpClientHelper;
import com.github.sample.retrofitrxjavasample.net.HttpClientHelper2;
import com.github.sample.retrofitrxjavasample.net.download.ProgressResponseListener;
import com.github.sample.retrofitrxjavasample.net.progress.DownloadProgressHandler;
import com.github.sample.retrofitrxjavasample.utils.FileUtils;
import com.github.sample.retrofitrxjavasample.utils.Util;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * 本例以获取豆瓣电影为例进行演示，请求结果以日志形式展示
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String url = "http://gdown.baidu.com/data/wisegame/20eb4c5985bfa304/weixin_861.apk";
    private String HOST = "http://gdown.baidu.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

    }

    /**
     * 待封装优化，最好能够只传入一个url，一个文件保存目录，一个下载进度回调即可下载
     */
    @OnClick(R.id.btn_download)
    public void downloadClick() {
        //下载进度更新回调在子线程，所以使用起来不够方便
        rxjavaDownload();
    }

    @OnClick(R.id.btn_download2)
    public void downloadClick2() {
        //使用retrofit下载，进度更新在主线程中，使用retrofit直接下载,但是缺点是在成功的回调onResponse中，
        //需要进行保存文件的操作，这个回调是在主线程中执行，所以要异步保存。
        download2();
    }

    @OnClick(R.id.btn_download3)
    public void downloadClick3() {
        //使用retrofit + rxjava下载，并且进度更新在主线程，保存文件的操作通过rxjava直接在子线程中执行，完美~
        download3();

    }

    private void download3() {
        final ProgressDialog dialog = createProgressDialog();

        DownloadProgressHandler progressHandler = new DownloadProgressHandler() {

            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.v(TAG,"3 onProgress 是否在主线程中运行:" +  Util.isMainThread());

                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done","--->" + String.valueOf(done));

                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        };

        Retrofit retrofit3 = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpClientHelper.addProgressResponseListener(progressHandler))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        retrofit3.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        Log.d(TAG,"map 是否在主线程中运行:" +  Util.isMainThread());
                        return responseBody.byteStream();
                    }
                })
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            //直接在子线程中保存文件
                            Log.d(TAG,"doOnNext 是否在主线程中运行:" +  Util.isMainThread());
                            File outputFile = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_DOWNLOADS), "file.apk");
                            FileUtils.writeFile(inputStream, outputFile);
                            Log.d(TAG,"inputStream:" + inputStream);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        Logger.d("onNext:" + inputStream);
                    }
                });
    }

    private void download2() {
        //监听下载进度

        final ProgressDialog dialog = createProgressDialog();

        HttpClientHelper2.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("2是否在主线程中运行", Util.isMainThread() + "");
                Log.e("onProgress",String.format("%d%% done\n",(100 * bytesRead) / contentLength));
                Log.e("done","--->" + String.valueOf(done));

                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpClientHelper2.addProgressResponseListener())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        DownloadService downloadService = retrofit2.create(DownloadService.class);

        Call<ResponseBody> call = downloadService.download2(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                //在主线程中如果直接操作io，会导致网络异常的错误，所以必须在子线程中处理
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream inputStream = response.body().byteStream();
                            File outputFile = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_DOWNLOADS), "file123.apk");
                            FileUtils.writeFile(inputStream, outputFile);
                            Logger.d("inputStream:" + inputStream);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Logger.d("onFailure:" + t.getMessage());
            }
        });
    }

    private void rxjavaDownload() {
        ProgressResponseListener progressResponseListener = new ProgressResponseListener() {

            @Override
            public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
                int progress = (int) ((bytesRead * 100) / contentLength);
                Log.e("是否在主线程中运行", Util.isMainThread() + "");
                Log.d(TAG,"bytesRead:" + bytesRead + ",contentLength:" + contentLength + ",当前下载进度：" + progress + "%");
            }
        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(HttpClientHelper.addProgressResponseListener(progressResponseListener))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        retrofit.create(DownloadService.class)
                .download(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        Logger.e("map 是否在主线程中运行:" +  Util.isMainThread());
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            Logger.e("doOnNext 是否在主线程中运行:" + Util.isMainThread());
                            File outputFile = new File(Environment.getExternalStoragePublicDirectory
                                    (Environment.DIRECTORY_DOWNLOADS), "file.apk");
                            FileUtils.writeFile(inputStream, outputFile);
                            Logger.d("inputStream:" + inputStream);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        Logger.d("onNext:" + inputStream);
                    }
                });
    }

    @OnClick(R.id.btn_post)
    public void postClick() {
        ApiManager.getMovieTop(0,10);
    }


    @OnClick(R.id.btn_post_rxjava)
    public void postRxJavaClick() {
        ApiManager.getMovieTopWithRxJava(0,10);
    }

    /**
     * 这是一个非常实用的操作，有时候我们需要请求两个不同的接口，将两个接口的数据都拿到后才能进行下一步的操作
     * 传统做法你需要请求两次网络，在第一次网络请求成功的回调里执行第二次网络请求和保存第一次请求的结果，然后
     * 在第二次网络请求成功的回调里合并这两次的结果，异常繁琐，而使用RxJava和Retrofit进行基于RxJava的zip
     * 操作，将会异常轻松，一个链式调用即可。
     * <br<
     * 本例将GankApi和DoubanApi的请求结果合并成一个TwoResultModel的结果
     */
    @OnClick(R.id.btn_rxjava_zip)
    public void zipRxJavaClick() {
        Observable.zip(ApiManager.getGankApi().getBeauties(200, 1),
                ApiManager.getDoubanApi().getMoiveTopWithRxJava(0,10),
                new Func2<GankBeauty, MovieModel, TwoResultModel>() {

                    @Override
                    public TwoResultModel call(GankBeauty gankBeauty, MovieModel movieModel) {
                        TwoResultModel model = new TwoResultModel();
                        model.setGankBeauty(gankBeauty);
                        model.setMovieModel(movieModel);
                        return model;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TwoResultModel>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e,"");
                    }

                    @Override
                    public void onNext(TwoResultModel twoResultModel) {
                        Logger.w("合并的结果获取成功...");
                        Logger.d(twoResultModel.toString());
                    }
                });
    }

    private ProgressDialog createProgressDialog() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

}
