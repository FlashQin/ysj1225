package com.kalacheng.commonview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.kalacheng.libuser.model.NobLiveGift;
import com.kalacheng.util.utils.CommonCallback;
import com.kalacheng.commonview.utils.GifCacheUtil;

import java.io.File;
import java.util.List;


public class DownloadService extends Service {

    private int giftDownloadCount = 0;
    public List<NobLiveGift> giftList;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            giftList = intent.getParcelableArrayListExtra("NobLiveGift");
            loadGiftFile();
        }
        return START_STICKY;
    }

    private void loadGiftFile() {
        if (null != giftList) {
            if (giftDownloadCount < giftList.size()) {
                if (!giftList.get(giftDownloadCount).swf.equals("")) {
                    String urlpath =  String.valueOf(giftList.get(giftDownloadCount).id);
                   /* if (giftList.get(giftDownloadCount).swf.endsWith(".svga")) {
                        urlpath = giftList.get(giftDownloadCount).swf.substring(giftList.get(giftDownloadCount).swf.length() - 16, giftList.get(giftDownloadCount).swf.length() - 5);
                    } else {
                        urlpath = giftList.get(giftDownloadCount).swf.substring(giftList.get(giftDownloadCount).swf.length() - 15, giftList.get(giftDownloadCount).swf.length() - 4);
                    }*/
                    GifCacheUtil.getFile(urlpath, giftList.get(giftDownloadCount).swf, new CommonCallback<File>() {
                        @Override
                        public void callback(File file) {
                            giftDownloadCount++;
                            loadGiftFile();
                        }
                    });
                }

            } else {
                stopSelf();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
