package com.kalacheng.commonview.music;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.kalacheng.libuser.model.AppMusicDTO;
import com.kalacheng.libuser.model.AppUserMusicDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxf on 2017/9/4.
 */

public class MusicDbManager1 {
    private static MusicDbManager1 sInstace;
    private MusicDbHelper1 mDbHelper;

    private MusicDbManager1() {
        mDbHelper = new MusicDbHelper1();
    }

    public static MusicDbManager1 getInstace() {
        if (sInstace == null) {
            synchronized (MusicDbManager1.class) {
                if (sInstace == null) {
                    sInstace = new MusicDbManager1();
                }
            }
        }
        return sInstace;
    }


    /**
     * 保存数据
     *
     * @param bean
     */
    public void save(AppUserMusicDTO bean) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor c = db.rawQuery(MusicDbHelper1.QUERY + "'" + bean.id + "'", null);
            if (c.moveToNext()) {//如果存在，执行update
                SQLiteStatement st = db.compileStatement(MusicDbHelper1.UPDATE);
                db.beginTransaction();
                try {
                    st.bindString(1, bean.name);
                    st.bindString(2, bean.author);
                    st.bindString(3, bean.cover);
                    st.bindLong(4, bean.id);
                    st.bindLong(5,bean.musicId);
                    st.executeUpdateDelete();
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
            } else {//如果不存在，执行insert
                SQLiteStatement st = db.compileStatement(MusicDbHelper1.INSERT);
                db.beginTransaction();
                try {
                    st.bindLong(1, bean.id);
                    st.bindString(2, bean.name);
                    st.bindString(3, bean.author);
                    st.bindString(4, bean.cover);
                    st.bindLong(5,bean.musicId);
                    st.executeInsert();
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }

    /**
     * 查询列表
     *
     * @return
     */
    public List<AppUserMusicDTO> queryList() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.isOpen()) {
            List<AppUserMusicDTO> list = new ArrayList<>();
            Cursor c = null;
            try {
                c = db.rawQuery(MusicDbHelper1.QUERY_LIST, null);
                while (c.moveToNext()) {
                    AppUserMusicDTO bean = new AppUserMusicDTO();
                    bean.id = c.getLong(c.getColumnIndex(MusicDbHelper1.ID));
                    bean.name = c.getString(c.getColumnIndex(MusicDbHelper1.NAME));
                    bean.author = c.getString(c.getColumnIndex(MusicDbHelper1.ARTIST));
                    bean.cover = c.getString(c.getColumnIndex(MusicDbHelper1.COVER));
                    bean.musicId = c.getLong(c.getColumnIndex(MusicDbHelper1.MusicID));
                    list.add(bean);
                }
            } finally {
                if (c != null) {
                    c.close();
                }
                db.close();
            }
            return list;
        }
        return null;
    }


    /**
     * 删除歌曲
     *
     * @param id
     */
    public void delete(long id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (db.isOpen()) {
            try {
                db.execSQL(MusicDbHelper1.DELETE + "'" + id + "'");
            } finally {
                db.close();
            }
        }
    }

    /**
     * 107      * 清除表的数据
     * 108
     */
    public void cleanUpData() {
        // delete from TableName;  //清空数据
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        String cleanUpDataSQL = "delete from " + MusicDbHelper1.TABLE_NAME;
        try {
            database.execSQL(cleanUpDataSQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != database) {
                database.close();
            }
        }
    }

    /*
    * 修改播放音乐状态
    * */

    public void UpData(){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
//        database.update(MusicDbHelper1.TABLE_NAME,)
    }
}
