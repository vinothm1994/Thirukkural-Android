package com.vinoth.thirukkural.data;

import android.content.Context;
import android.database.Cursor;

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
    private final KuralDbHelper kuralDbHelper;

    public AppDataManager(Context context) {
        KuralDbHelper.intiDb(context);
        kuralDbHelper = KuralDbHelper.getInstance();
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

    public List<KuralDetail> getAllKuralDetails() {
        Cursor cursor = kuralDbHelper.getReadableDatabase()
                .query(DBContract.KuralContract.TABLE_NAME, null, null, null, null, null, null);
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

    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter,KuralDetail>> getAll(){

        List<KuralSection> section=getAllkuralSection();
        Map<Integer,KuralSection> sectionHashMap=new HashMap<>(section.size());
        for (KuralSection i : section) sectionHashMap.put(i.getId(),i);
        List<KuralChapterGroup> groups = getAllChaptergroups();
        Map<Integer,KuralChapterGroup> kuralChapterGroupMap=new HashMap<>(groups.size());
        for (KuralChapterGroup i : groups) kuralChapterGroupMap.put(i.getId(),i);
        List<KuralChapter> chapters=getAllChapters();
        Map<Integer,KuralChapter> kuralChapterMap=new HashMap<>();
        for (KuralChapter kuralChapter:chapters) kuralChapterMap.put(kuralChapter.getId(),kuralChapter);
        List<KuralDetail> kuralDetails=getAllKuralDetails();
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter,KuralDetail>>quartets=new ArrayList<>();
        for (int i = 0; i < kuralDetails.size(); i++) {
            KuralDetail kuralDetail = kuralDetails.get(i);
            KuralChapter kuralChapter = kuralChapterMap.get(kuralDetail.getChapterId());
            KuralChapterGroup chapterGroup = kuralChapterGroupMap.get(kuralChapter.getChapterGroupId());
            KuralSection kuralSection = sectionHashMap.get(chapterGroup.getSectionId());
            quartets.add(new Quartet<>(kuralSection,chapterGroup,kuralChapter,kuralDetail));
        }

        return quartets;
    }


}
