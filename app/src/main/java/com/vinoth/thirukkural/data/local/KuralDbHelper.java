package com.vinoth.thirukkural.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class KuralDbHelper extends SQLiteOpenHelper {

    public static final String KURAL_DB = "kural.db";
    private static final int version = 1;
    private static String QUA = "\"";
    private static KuralDbHelper instance;
    private String TAG = "xxxxxxxxxxxx";

    public KuralDbHelper(@Nullable Context context) {
        super(context, KURAL_DB, null, version);

    }

    public static void intiDb(Context context) {
        if (instance == null)
            instance = new KuralDbHelper(context.getApplicationContext());
    }

    public static KuralDbHelper getInstance() {
        if (instance == null)
            throw new IllegalArgumentException("db not init");
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sectionTB = "CREATE TABLE " + QUA + DBContract.SectionContract.TABLE_NAME + QUA + "(" + QUA + DBContract.SectionContract.ID + QUA + "INTEGER NOT NULL, " + QUA + DBContract.SectionContract.TAMIL + QUA + " TEXT, " + QUA + DBContract.SectionContract.ENGLISH + QUA + "TEXT," + QUA + DBContract.SectionContract.TRANSLITERATION + QUA + " TEXT, PRIMARY KEY(" + QUA + DBContract.SectionContract.ID + QUA + ") )";
        db.execSQL(sectionTB);
        sectionTB = "CREATE TABLE " + QUA + DBContract.ChapterGroupContract.TABLE_NAME + QUA + "(" + QUA + DBContract.ChapterGroupContract.ID + QUA + "INTEGER NOT NULL, " + QUA + DBContract.ChapterGroupContract.SECTION_ID + QUA + "INTEGER NOT NULL, " + QUA + DBContract.ChapterGroupContract.TAMIL + QUA + " TEXT, " + QUA + DBContract.ChapterGroupContract.ENGLISH + QUA + "TEXT," + QUA + DBContract.ChapterGroupContract.TRANSLITERATION + QUA + " TEXT, PRIMARY KEY(" + QUA + DBContract.ChapterGroupContract.ID + QUA + ") )";
        db.execSQL(sectionTB);
        sectionTB = "CREATE TABLE " + QUA + DBContract.ChapterContract.TABLE_NAME + QUA + "(" + QUA + DBContract.ChapterContract.ID + QUA + "INTEGER NOT NULL, " + QUA + DBContract.ChapterContract.CHAPTER_GROUP_ID + QUA + " INTEGER NOT NULL, " + QUA + DBContract.ChapterContract.TAMIL + QUA + " TEXT, " + QUA + DBContract.ChapterContract.ENGLISH + QUA + "TEXT," + QUA + DBContract.ChapterContract.TRANSLITERATION + QUA + " TEXT, PRIMARY KEY(" + QUA + DBContract.ChapterContract.ID + QUA + ") )";
        db.execSQL(sectionTB);
        String sql = "CREATE TABLE " + QUA + DBContract.KuralContract.TABLE_NAME + QUA + " ( " + QUA + DBContract.KuralContract.ID + QUA + " INTEGER NOT NULL, " + QUA + DBContract.KuralContract.CHAPTER_ID + QUA + " INTEGER NOT NULL, " + QUA + DBContract.KuralContract.TAMIL + QUA + " TEXT, " + QUA + DBContract.KuralContract.ENGLISH + QUA + " TEXT, " + QUA + DBContract.KuralContract.TRANSLITERATION + QUA + " TEXT, " + QUA + DBContract.KuralContract.MV_EXP + QUA + " TEXT, " + QUA + DBContract.KuralContract.SP_EXP + QUA + " TEXT," + QUA + DBContract.KuralContract.MK_EXP + QUA + " TEXT, " + QUA + DBContract.KuralContract.COUPLET + QUA + " TEXT, "+ QUA + DBContract.KuralContract.ENG_EXP + QUA + " TEXT, " + QUA + DBContract.KuralContract.BOOKMARK + QUA + " INTEGER, PRIMARY KEY(" + QUA + DBContract.KuralContract.ID + QUA + ") )";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
