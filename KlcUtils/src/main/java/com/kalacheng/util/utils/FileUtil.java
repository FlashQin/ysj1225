package com.kalacheng.util.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.Formatter;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Copyright (C) 2018
 * 版权所有
 *
 * 功能描述:  文件相关工具类
 * 作者：
 * 创建时间：2018/6/25
 *
 * 修改人：
 * 修改描述：
 * 修改日期
 */
public class FileUtil {
    /**
     * 适配7.0获取Uri
     */
    public static Uri getUriAdjust24(Context context, String path, File file) {
        Uri pictureUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果是7.0android系统
            pictureUri = FileProvider.getUriForFile(context, path, file);
        } else {
            pictureUri = Uri.fromFile(file);
        }
        return pictureUri;
    }


    /**
     * 获取大于api19时获取相册中图片真正的uri
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathAbove19(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * 删除目录
     */
    public static boolean deleteFiles(String path) {
        File file = new File(path);
        boolean flag = false;
        if (!file.exists()) {
            return flag;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            if (fileList != null && fileList.length > 0) {
                for (int i = 0; i < fileList.length; i++) {
                    flag = deleteFiles(fileList[i].getAbsolutePath());
                }
            }
        }
        return flag;
    }

    /**
     * 获取缓存数据文件夹的路径
     *
     * @return 缓存在这里的好处是:不用自己再去手动创建文件夹,不用担心用户把自己创建的文件夹删掉,在应用程序卸载的时候,这里会被清空,
     * 使用第三方的清理工具的时候,这里也会被清空.
     */
    public static File getCacheDir() {
        return ApplicationUtil.getInstance().getCacheDir();
    }

    /**
     * 判断是否为文件
     *
     * @param filePath 文件路径
     */
    public static boolean isFile(String filePath) {
        if (filePath.indexOf("/") != 0) {
            filePath = "/" + filePath;
        }
        File mFile = new File(filePath);
        if (mFile.exists() && mFile.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断文件是否为文件夹
     *
     * @param folderPath 文件夹路径
     */
    public static boolean isFolder(String folderPath) {
        if (folderPath.indexOf("/") != 0) {
            folderPath = "/" + folderPath;
        }
        File mFile = new File(folderPath);
        if (mFile.exists() && mFile.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断文件(夹)是否存在
     *
     * @param filePath 文件路径
     */
    public static boolean isExists(String filePath) {
        if (isFolder(filePath)) {
            return true;
        }
        if (isFile(filePath)) {
            return true;
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param folderPath 文件夹路径
     * @return
     */
    public static boolean createFolder(String folderPath) {
        if (folderPath.indexOf("/") != 0) {
            folderPath = "/" + folderPath;
        }
        File folder = new File(folderPath);
        if (folder.exists()) {
            return true;
        } else {
            return folder.mkdirs();
        }
    }

    /**
     * 删除文件(夹)
     *
     * @param file 文件(夹)
     */
    public static boolean deleteFile(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFile(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if (file != null && file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 计算文件（夹）大小
     *
     * @param file 文件（夹）
     * @return 大小（千字节）
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFileSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化文件大小
     *
     * @return B, KB, MB, GB等
     */
    public static String formatFileSize(long number) {
        return Formatter.formatFileSize(ApplicationUtil.getInstance(), number);
    }

    /**
     * 向文件中逐行写入数据
     * FileUtils.writeFileLine(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"building"+ File.separator+"test.txt","测试数据");
     */
    public static void writeFileLine(String filePath, String str) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            String c = str + "\r\n";
            fw.write(c);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 排序
     */
    public static void sort(List<File> files) {
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o2.getName().compareTo(o1.getName());
            }
        });
    }

    /**
     * 把字符串保存成文件
     */
    public static void saveStringToFile(File dir, String content, String fileName) {
        PrintWriter writer = null;
        try {
            FileOutputStream os = new FileOutputStream(new File(dir, fileName));
            writer = new PrintWriter(os);
            writer.write(content);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        String scheme = uri.getScheme();

        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                //微信文件打开的uri
                path = uri.getPath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && path != null && path.startsWith("/external")) {
                    return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + path.replace("/external", "")).getPath();
                } else {
                    String[] paths = uri.getPath().split("/0/");
                    if (paths.length == 2) {
                        return Environment.getExternalStorageDirectory() + "/" + paths[1];
                    }
                }
            } else {
                String[] paths = uri.getPath().split("/0/");
                if (paths.length == 2) {
                    return Environment.getExternalStorageDirectory() + "/" + paths[1];
                }

            }
        }
        return null;
    }

    /**
     * 创建文件随机名称，不包含后缀
     */
    public static String generateFileName() {
        return "android_" + new DateUtil().getDateToFormat("yyyyMMdd_HHmmss_SSS") + RandomUtil.nextInt(9999);
    }
}
