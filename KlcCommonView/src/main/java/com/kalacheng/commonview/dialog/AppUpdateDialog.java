package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.WebUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.download.DownloadUtil;
import com.kalacheng.util.view.ApkDownloadProgressView;

import java.io.File;

import cc.shinichi.library.tool.utility.file.FileUtil;

public class AppUpdateDialog extends DialogFragment {
    protected Context mContext;
    protected View mRootView;

    private ImageView ivVersionClose;
    private TextView tvVersionUpdateCode;
    private TextView tvVersionUpdateTip;
    private TextView tvVersionUpdateConfirm;
    private ApkDownloadProgressView pvDownload;
    private String mApkUrl;
    private String mApkPath;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_version_update, null);

        Dialog dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(mRootView);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setCancelable(false);
        ivVersionClose = mRootView.findViewById(R.id.ivVersionClose);
        tvVersionUpdateCode = mRootView.findViewById(R.id.tvVersionUpdateCode);
        tvVersionUpdateTip = mRootView.findViewById(R.id.tvVersionUpdateTip);
        tvVersionUpdateConfirm = mRootView.findViewById(R.id.tvVersionUpdateConfirm);
        pvDownload = mRootView.findViewById(R.id.pvDownload);

        Bundle bundle = getArguments();
        if (bundle != null) {
            tvVersionUpdateCode.setText("最新版本：" + bundle.getString("versionName"));
            tvVersionUpdateTip.setText(bundle.getString("versionTip"));
            mApkUrl = bundle.getString("downloadRealUrl");
            if (bundle.getBoolean("force", false)) {
                ivVersionClose.setVisibility(View.GONE);
            } else {
                ivVersionClose.setVisibility(View.VISIBLE);
            }
        }
        mApkPath = mContext.getFilesDir().getAbsolutePath() + "/apk/";

        ivVersionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvVersionUpdateConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mApkUrl)) {
                    if (mApkUrl.endsWith(".apk")) {
                        ivVersionClose.setVisibility(View.GONE);
                        tvVersionUpdateConfirm.setVisibility(View.GONE);
                        pvDownload.setVisibility(View.VISIBLE);
                        downloadApk();
                    } else {
                        if (!WebUtil.goExternalWeb(mContext, mApkUrl)) {
                            ToastUtil.show(R.string.version_download_url_error);
                            dismiss();
                        }
                    }
                } else {
                    ToastUtil.show(R.string.version_download_url_error);
                    dismiss();
                }
            }
        });
    }

    private void downloadApk() {
        File dir = new File(mApkPath);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }

        DownloadUtil downloadUtil = new DownloadUtil();
        downloadUtil.download("downloadApk", dir, "flavor.apk", mApkUrl, new DownloadUtil.Callback() {
            @Override
            public void onSuccess(File file) {
                tvVersionUpdateConfirm.setVisibility(View.VISIBLE);
                pvDownload.setVisibility(View.GONE);
                tvVersionUpdateConfirm.setText("重新下载");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (!mContext.getPackageManager().canRequestPackageInstalls()) {
                        //打开权限
                        Uri packageURI = Uri.parse("package:" + mContext.getApplicationContext().getPackageName());
                        //注意这个是8.0新API
                        try {
                            Intent intent1 = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                            startActivity(intent1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        installApp(file);
                    } else {
                        //有权限，直接安装
                        installApp(file);
                    }
                } else {
                    //安卓8.0以下，直接安装
                    installApp(file);
                }
            }

            @Override
            public void onProgress(int progress) {
                pvDownload.setProgress(progress);
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.show("下载失败，请重新下载");
                tvVersionUpdateConfirm.setVisibility(View.VISIBLE);
                pvDownload.setVisibility(View.GONE);
                tvVersionUpdateConfirm.setText("重新下载");
            }
        });
    }

    /**
     * 安装APK
     */
    private void installApp(File file) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".fileprovider", file), "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(intent);
            } else {
                installApp(file.getPath());
            }
        } catch (Exception e) {
            dismiss();
            ToastUtil.show("升级失败");
        }
    }

    /**
     * 安装APK
     */
    private void installApp(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (FileUtil.isFile(filePath)) {
            intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

}
