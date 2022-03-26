package com.kalacheng.commonview.music;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.util.utils.L;

/**
 * Created by cxf on 2017/9/4.
 */

public class MusicDbHelper1 extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kalacheng.music";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "voicemusic";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ARTIST = "artist";
    public static final String COVER = "cover";
    public static final String MusicID = "musicid";
    public static final String INSERT = "INSERT INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE " + TABLE_NAME + " SET " + NAME + "=?," + ARTIST + "=?," + COVER + "=?," + MusicID + "=? WHERE " + ID + "=?";
    public static final String QUERY_LIST = "SELECT * FROM " + TABLE_NAME;
    public static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE id=";
    public static final String QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE id=";


    public MusicDbHelper1() {
        super(BaseApplication.getInstance(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " VARCHAR PRIMARY KEY ," + NAME + " VARCHAR, " + ARTIST + " VARCHAR, " + COVER + " VARCHAR, " + MusicID + " VARCHAR)";
        L.e("MusicDbHelper----sql--->" + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ID + " VARCHAR PRIMARY KEY ," + NAME + " VARCHAR, " + ARTIST + " VARCHAR, " + COVER + " VARCHAR, " + MusicID + " VARCHAR)";
            L.e("MusicDbHelper----sql--->" + sql);
            db.execSQL(sql);
        }
    }


}
