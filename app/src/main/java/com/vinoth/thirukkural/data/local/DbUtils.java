package com.vinoth.thirukkural.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbUtils {


    public static boolean copyKuralDb(Context context) throws IOException {
        InputStream myInput = context.getResources().openRawResource(R.raw.thirukkural);
        // Path to the just created empty db
        String DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        String outFileName = DB_PATH + KuralDbHelper.KURAL_DB;
        File dbFile = new File(outFileName);
        dbFile.getParentFile().mkdirs();
        if (dbFile.exists())
            return true;
        OutputStream myOutput = new FileOutputStream(dbFile);
        byte[] buffer = new byte[2048];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        return false;

    }


    public static void insert(List<KuralSection> kuralSections) {
        KuralDbHelper kuralDbHelper = KuralDbHelper.getInstance();
        SQLiteDatabase db = kuralDbHelper.getWritableDatabase();
        db.beginTransaction();
        for (KuralSection kuralSection : kuralSections) {
            ContentValues secCv = new ContentValues();
            secCv.put(DBContract.SectionContract.ID, kuralSection.getId());
            secCv.put(DBContract.SectionContract.TAMIL, kuralSection.getTamilName());
            secCv.put(DBContract.SectionContract.ENGLISH, kuralSection.getName());
            secCv.put(DBContract.SectionContract.TRANSLITERATION, kuralSection.getTransliteration());

            db.insert(DBContract.SectionContract.TABLE_NAME, null, secCv);

            for (KuralChapterGroup chGrp : kuralSection.getKuralChapterGroups()) {
                ContentValues chGrpCv = new ContentValues();
                chGrpCv.put(DBContract.ChapterGroupContract.ID, chGrp.getId());
                chGrpCv.put(DBContract.ChapterGroupContract.SECTION_ID, chGrp.getSectionId());
                chGrpCv.put(DBContract.ChapterGroupContract.TAMIL, chGrp.getTamilName());
                chGrpCv.put(DBContract.ChapterGroupContract.ENGLISH, chGrp.getName());
                chGrpCv.put(DBContract.ChapterGroupContract.TRANSLITERATION, chGrp.getTransliteration());

                db.insert(DBContract.ChapterGroupContract.TABLE_NAME, null, chGrpCv);

                for (KuralChapter kuralChapter : chGrp.getKuralChapters()) {
                    ContentValues chCv = new ContentValues();
                    chCv.put(DBContract.ChapterContract.ID, kuralChapter.getId());
                    chCv.put(DBContract.ChapterContract.CHAPTER_GROUP_ID, kuralChapter.getChapterGroupId());
                    chCv.put(DBContract.ChapterContract.TAMIL, kuralChapter.getTamilName());
                    chCv.put(DBContract.ChapterContract.ENGLISH, kuralChapter.getName());
                    chCv.put(DBContract.ChapterContract.TRANSLITERATION, kuralChapter.getTransliteration());

                    db.insert(DBContract.ChapterContract.TABLE_NAME, null, chCv);

                    for (KuralDetail kuralDetail : kuralChapter.getKuralDetails()) {
                        ContentValues kuralDetailCV = new ContentValues();
                        kuralDetailCV.put(DBContract.KuralContract.ID, kuralDetail.getId());
                        kuralDetailCV.put(DBContract.KuralContract.CHAPTER_ID, kuralDetail.getChapterId());
                        kuralDetailCV.put(DBContract.KuralContract.TAMIL, kuralDetail.getKuralInTamil());
                        kuralDetailCV.put(DBContract.KuralContract.ENGLISH, kuralDetail.getKuralInEng());
                        kuralDetailCV.put(DBContract.KuralContract.TRANSLITERATION, kuralDetail.getKuralTransliteration());
                        kuralDetailCV.put(DBContract.KuralContract.MV_EXP, kuralDetail.getMvExp());
                        kuralDetailCV.put(DBContract.KuralContract.SP_EXP, kuralDetail.getSpExp());
                        kuralDetailCV.put(DBContract.KuralContract.MK_EXP, kuralDetail.getMkExp());
                        kuralDetailCV.put(DBContract.KuralContract.COUPLET, kuralDetail.getCouplet());
                        kuralDetailCV.put(DBContract.KuralContract.ENG_EXP, kuralDetail.getEngExp());
                        db.insert(DBContract.KuralContract.TABLE_NAME, null, kuralDetailCV);

                    }

                }

            }

        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public static List<KuralDetail> parserKuralDetail(Cursor cursor) {
        List<KuralDetail> kuralDetails = new ArrayList<>(cursor.getCount());
        int chpcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.CHAPTER_ID);
        int tamilcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.TAMIL);
        int engcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.ENGLISH);
        int litcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.TRANSLITERATION);
        int engExpcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.ENG_EXP);
        int mvcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.MV_EXP);
        int mkcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.MK_EXP);
        int spcolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.SP_EXP);
        int coucolumnIndex = cursor.getColumnIndex(DBContract.KuralContract.COUPLET);
        while (cursor.moveToNext()) {
            KuralDetail kuralDetail = new KuralDetail();
            int id = cursor.getColumnIndex(DBContract.KuralContract.ID);
            kuralDetail.setId(cursor.getInt(id));
            kuralDetail.setChapterId(cursor.getInt(chpcolumnIndex));
            kuralDetail.setKuralInTamil(cursor.getString(tamilcolumnIndex));
            kuralDetail.setKuralInEng(cursor.getString(engcolumnIndex));
            kuralDetail.setKuralTransliteration(cursor.getString(litcolumnIndex));
            kuralDetail.setEngExp(cursor.getString(engExpcolumnIndex));
            kuralDetail.setMvExp(cursor.getString(mvcolumnIndex));
            kuralDetail.setMkExp(cursor.getString(mkcolumnIndex));
            kuralDetail.setSpExp(cursor.getString(spcolumnIndex));
            kuralDetail.setCouplet(cursor.getString(coucolumnIndex));
            kuralDetails.add(kuralDetail);
        }
        return kuralDetails;
    }

    public static List<KuralSection> parserSection(Cursor cursor){
        List<KuralSection> kuralSections=new ArrayList<>();
        while (cursor.moveToNext()){
            KuralSection kuralSection=new KuralSection();
            kuralSection.setId(cursor.getInt(cursor.getColumnIndex(DBContract.SectionContract.ID)));
            kuralSection.setName(cursor.getString(cursor.getColumnIndex(DBContract.SectionContract.ENGLISH)));
            kuralSection.setTamilName(cursor.getString(cursor.getColumnIndex(DBContract.SectionContract.TAMIL)));
            kuralSection.setTransliteration(cursor.getString(cursor.getColumnIndex(DBContract.SectionContract.TRANSLITERATION)));
            kuralSections.add(kuralSection);
        }
        return kuralSections;
    }

    public static List<KuralChapterGroup> parserChaptergroups(Cursor cursor){
        List<KuralChapterGroup> kuralChapterGroups =new ArrayList<>();
        while (cursor.moveToNext()){
            KuralChapterGroup kuralSection=new KuralChapterGroup();
            kuralSection.setId(cursor.getInt(cursor.getColumnIndex(DBContract.ChapterGroupContract.ID)));
            kuralSection.setSectionId(cursor.getInt(cursor.getColumnIndex(DBContract.ChapterGroupContract.SECTION_ID)));
            kuralSection.setName(cursor.getString(cursor.getColumnIndex(DBContract.ChapterGroupContract.ENGLISH)));
            kuralSection.setTamilName(cursor.getString(cursor.getColumnIndex(DBContract.ChapterGroupContract.TAMIL)));
            kuralSection.setTransliteration(cursor.getString(cursor.getColumnIndex(DBContract.ChapterGroupContract.TRANSLITERATION)));
            kuralChapterGroups.add(kuralSection);
        }
        return kuralChapterGroups;
    }

    public static List<KuralChapter> parserChapter(Cursor cursor){
        List<KuralChapter> kuralChaptergroups=new ArrayList<>();
        int columnIndex = cursor.getColumnIndex(DBContract.ChapterContract.ID);
        int columnIndex1 = cursor.getColumnIndex(DBContract.ChapterContract.CHAPTER_GROUP_ID);
        int columnIndex2 = cursor.getColumnIndex(DBContract.ChapterContract.ENGLISH);
        int columnIndex3 = cursor.getColumnIndex(DBContract.ChapterContract.TAMIL);
        int columnIndex4 = cursor.getColumnIndex(DBContract.ChapterContract.TRANSLITERATION);
        while (cursor.moveToNext()){
            KuralChapter kuralSection=new KuralChapter();
            kuralSection.setId(cursor.getInt(columnIndex));
            kuralSection.setChapterGroupId(cursor.getInt(columnIndex1));
            kuralSection.setName(cursor.getString(columnIndex2));
            kuralSection.setTamilName(cursor.getString(columnIndex3));
            kuralSection.setTransliteration(cursor.getString(columnIndex4));
            kuralChaptergroups.add(kuralSection);
        }
        cursor.close();
        return kuralChaptergroups;
    }

}
