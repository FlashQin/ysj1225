package com.kalacheng.util.utils.download;

import com.kalacheng.util.utils.L;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * Created by cxf on 2017/9/4.
 */

public class DownloadUtil {
    private static class SingletonHolder {
        private static final DownloadUtil INSTANCE = new DownloadUtil();
    }

    public DownloadUtil() {
    }

    public static final DownloadUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void download(String tag, File fileDir, final String fileName, String url, final Callback callback) {
        OkGo.<File>get(url).tag(tag).retryCount(3).execute(new FileCallback(fileDir.getAbsolutePath(), fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                //下载成功结束后的回调
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void downloadProgress(Progress progress) {
                if (callback != null) {
                    int val = (int) (progress.currentSize * 100 / progress.totalSize);
                    L.e(fileName + "下载进度--->" + val);
                    callback.onProgress(val);
                }
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Throwable e = response.getException();
                L.e("下载失败--->" + e);
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }


    public interface Callback {
        void onSuccess(File file);

        void onProgress(int progress);

        void onError(Throwable e);
    }
}
