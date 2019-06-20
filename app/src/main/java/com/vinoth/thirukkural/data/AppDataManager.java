package com.vinoth.thirukkural.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.NonNull;

import com.vinoth.thirukkural.data.local.DBContract;
import com.vinoth.thirukkural.data.local.DbUtils;
import com.vinoth.thirukkural.data.local.KuralDbHelper;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;

import org.javatuples.Quartet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDataManager {
    private static final String TAG = "AppDataManager";
    private static AppDataManager appDataManager;
    private final KuralDbHelper kuralDbHelper;

    public AppDataManager(Context context) {
        KuralDbHelper.intiDb(context);
        kuralDbHelper = KuralDbHelper.getInstance();
    }

    public static AppDataManager getInstance(@NonNull Context context) {
        if (appDataManager == null)
            appDataManager = new AppDataManager(context.getApplicationContext());
        return appDataManager;
    }

    public static AppDataManager getInstance() {
        if (appDataManager == null)
            throw new RuntimeException("AppDataManager not init");
        return appDataManager;
    }


    public List<KuralSection> getAllkuralSection() {
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.SectionContract.TABLE_NAME, null, null, null, null, null, null);
        List<KuralSection> kuralDetails = DbUtils.parserSection(cursor);
        cursor.close();
        return kuralDetails;
    }

    public KuralSection getAllkuralSection(int id) {
        String sele = DBContract.SectionContract.ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.SectionContract.TABLE_NAME, null, sele, new String[]{String.valueOf(id)}, null, null, null);
        List<KuralSection> kuralDetails = DbUtils.parserSection(cursor);
        cursor.close();
        return kuralDetails.get(0);
    }


    //**************************** ChapterGroup
    public List<KuralChapterGroup> getAllChaptergroups() {
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterGroupContract.TABLE_NAME, null, null, null, null, null, null);
        List<KuralChapterGroup> kuralDetails = DbUtils.parserChaptergroups(cursor);
        cursor.close();
        return kuralDetails;
    }

    public List<KuralChapterGroup> getAllChapterGroupsBySecId(int id) {
        String sele = DBContract.ChapterGroupContract.SECTION_ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterGroupContract.TABLE_NAME, null, sele, new String[]{String.valueOf(id)}, null, null, null);
        List<KuralChapterGroup> kuralDetails = DbUtils.parserChaptergroups(cursor);
        cursor.close();
        return kuralDetails;
    }

    public KuralChapterGroup getAllChaptergroupsById(int id) {
        String sele = DBContract.ChapterGroupContract.ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterGroupContract.TABLE_NAME, null, sele, new String[]{String.valueOf(id)}, null, null, null);
        List<KuralChapterGroup> kuralDetails = DbUtils.parserChaptergroups(cursor);
        cursor.close();
        return kuralDetails.get(0);
    }


    //**************************** Chapters
    public List<KuralChapter> getAllChapters() {
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterContract.TABLE_NAME, null, null, null, null, null, null);
        List<KuralChapter> kuralDetails = DbUtils.parserChapter(cursor);
        cursor.close();
        return kuralDetails;
    }

    public List<KuralChapter> getAllChapterByGroupId(int id) {
        String sele = DBContract.ChapterContract.CHAPTER_GROUP_ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterContract.TABLE_NAME, null, sele, new String[]{String.valueOf(id)}, null, null, null);
        List<KuralChapter> kuralDetails = DbUtils.parserChapter(cursor);
        cursor.close();
        return kuralDetails;
    }

    public KuralChapter getAllChapterById(int id) {
        String sele = DBContract.ChapterContract.ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.ChapterContract.TABLE_NAME, null, sele, new String[]{String.valueOf(id)}, null, null, null);
        List<KuralChapter> kuralDetails = DbUtils.parserChapter(cursor);
        cursor.close();
        return kuralDetails.get(0);
    }

    //**************************** kural


    public void updateBookmark(int id, boolean isBookmark) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.KuralContract.BOOKMARK, isBookmark ? 1 : 0);
        SQLiteDatabase writableDatabase = kuralDbHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        writableDatabase.update(DBContract.KuralContract.TABLE_NAME, contentValues, DBContract.KuralContract.ID + "=?", new String[]{String.valueOf(id)});
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();
    }

    public List<KuralDetail> getAllKuralDetails() {
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.KuralContract.TABLE_NAME, null, null, null, null, null, null);
        List<KuralDetail> kuralDetails = DbUtils.parserKuralDetail(cursor);
        cursor.close();
        return kuralDetails;
    }

    public List<KuralDetail> getAllKuralDetailsByKey(String key, int chapterId) {
        String filter = "%" + key + "%";
        String cha = "";
        if (chapterId != 0)
            cha = " AND " + DBContract.KuralContract.CHAPTER_ID + "=" + chapterId;
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.KuralContract.TABLE_NAME, null, "(" + DBContract.KuralContract.TAMIL + " LIKE ? OR " + DBContract.KuralContract.ENGLISH + " LIKE ? )" + cha, new String[]{filter, filter}, null, null, null);
        List<KuralDetail> kuralDetails = DbUtils.parserKuralDetail(cursor);
        cursor.close();
        return kuralDetails;
    }


    public List<KuralDetail> getAllKuralDetailsByChapterId(int chapterId) {
        String sele = DBContract.KuralContract.CHAPTER_ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.KuralContract.TABLE_NAME, null, sele, new String[]{String.valueOf(chapterId)}, null, null, null);
        List<KuralDetail> kuralDetails = DbUtils.parserKuralDetail(cursor);
        cursor.close();
        return kuralDetails;
    }

    public KuralDetail getAllKuralDetailsByKuralId(int kuralId) {
        String sele = DBContract.KuralContract.ID + "=?";
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.KuralContract.TABLE_NAME, null, sele, new String[]{String.valueOf(kuralId)}, null, null, null);
        List<KuralDetail> kuralDetails = DbUtils.parserKuralDetail(cursor);
        cursor.close();
        return kuralDetails.get(0);
    }

    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> getAll() {
        List<KuralSection> section = getAllkuralSection();
        Map<Integer, KuralSection> sectionHashMap = new HashMap<>(section.size());
        for (KuralSection i : section) sectionHashMap.put(i.getId(), i);
        List<KuralChapterGroup> groups = getAllChaptergroups();
        Map<Integer, KuralChapterGroup> kuralChapterGroupMap = new HashMap<>(groups.size());
        for (KuralChapterGroup i : groups) kuralChapterGroupMap.put(i.getId(), i);
        List<KuralChapter> chapters = getAllChapters();
        Map<Integer, KuralChapter> kuralChapterMap = new HashMap<>();
        for (KuralChapter kuralChapter : chapters)
            kuralChapterMap.put(kuralChapter.getId(), kuralChapter);
        List<KuralDetail> kuralDetails = getAllKuralDetails();
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> quartets = new ArrayList<>();
        for (int i = 0; i < kuralDetails.size(); i++) {
            KuralDetail kuralDetail = kuralDetails.get(i);
            KuralChapter kuralChapter = kuralChapterMap.get(kuralDetail.getChapterId());
            KuralChapterGroup chapterGroup = kuralChapterGroupMap.get(kuralChapter.getChapterGroupId());
            KuralSection kuralSection = sectionHashMap.get(chapterGroup.getSectionId());
            quartets.add(new Quartet<>(kuralSection, chapterGroup, kuralChapter, kuralDetail));
        }

        return quartets;
    }

    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> getAllByChapterId(int chapterId) {
        List<KuralSection> section = getAllkuralSection();
        Map<Integer, KuralSection> sectionHashMap = new HashMap<>(section.size());
        for (KuralSection i : section)
            sectionHashMap.put(i.getId(), i);
        List<KuralChapterGroup> groups = getAllChaptergroups();
        Map<Integer, KuralChapterGroup> kuralChapterGroupMap = new HashMap<>(groups.size());
        for (KuralChapterGroup i : groups)
            kuralChapterGroupMap.put(i.getId(), i);
        List<KuralChapter> chapters = getAllChapters();
        Map<Integer, KuralChapter> kuralChapterMap = new HashMap<>();
        for (KuralChapter kuralChapter : chapters)
            kuralChapterMap.put(kuralChapter.getId(), kuralChapter);
        List<KuralDetail> kuralDetails = getAllKuralDetailsByChapterId(chapterId);
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> quartets = new ArrayList<>();
        for (int i = 0; i < kuralDetails.size(); i++) {
            KuralDetail kuralDetail = kuralDetails.get(i);
            KuralChapter kuralChapter = kuralChapterMap.get(kuralDetail.getChapterId());
            KuralChapterGroup chapterGroup = kuralChapterGroupMap.get(kuralChapter.getChapterGroupId());
            KuralSection kuralSection = sectionHashMap.get(chapterGroup.getSectionId());
            quartets.add(new Quartet<>(kuralSection, chapterGroup, kuralChapter, kuralDetail));
        }

        return quartets;
    }

    //****Search Kural


    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> searchKural(int sectionId, int groupId, int chapterId, String key) {

        Log.i(TAG, String.format("searchKural: section:%d group:%d chap:%d key:%s", sectionId, groupId, chapterId, key));
        List<KuralSection> section = getAllkuralSection();
        SparseArray<KuralSection> sectionHashMap = new SparseArray<KuralSection>(section.size());
        for (KuralSection i : section) sectionHashMap.put(i.getId(), i);
        List<KuralChapterGroup> groups;
        if (sectionId == 0)
            groups = getAllChaptergroups();
        else
            groups = getAllChapterGroupsBySecId(sectionId);

        SparseArray<KuralChapterGroup> kuralChapterGroupMap = new SparseArray<>(groups.size());
        for (KuralChapterGroup i : groups) kuralChapterGroupMap.put(i.getId(), i);

        List<KuralChapter> chapters;
        if (groupId == 0)
            chapters = getAllChapters();
        else
            chapters = getAllChapterByGroupId(groupId);
        SparseArray<KuralChapter> kuralChapterMap = new SparseArray<>();
        for (KuralChapter kuralChapter : chapters)
            kuralChapterMap.put(kuralChapter.getId(), kuralChapter);

        List<KuralDetail> kuralDetails = getAllKuralDetailsByKey(key, chapterId);
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> quartets = new ArrayList<>();
        for (int i = 0; i < kuralDetails.size(); i++) {
            KuralDetail kuralDetail = kuralDetails.get(i);
            KuralChapter kuralChapter = kuralChapterMap.get(kuralDetail.getChapterId());
            if (kuralChapter == null)
                continue;
            KuralChapterGroup chapterGroup = kuralChapterGroupMap.get(kuralChapter.getChapterGroupId());
            if (chapterGroup == null)
                continue;
            KuralSection kuralSection = sectionHashMap.get(chapterGroup.getSectionId());
            if (kuralSection == null)
                continue;
            quartets.add(new Quartet<>(kuralSection, chapterGroup, kuralChapter, kuralDetail));
        }

        return quartets;
    }


}
