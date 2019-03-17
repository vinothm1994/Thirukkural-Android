package com.vinoth.thirukkural.ui.home;

public interface KuralScreenListener {

    void launchSection();

    void launchChapterGroup();

    void launchChapterGroup(int secId);

    void launchChapter();

    void launchChapter(int chapterGroupId);

    void launchKuralList(int chapterId);

    void launchAllKuralList();

    void launchKuralDetail(int kuralId);


}
