package com.vinoth.thirukkural.data.model;

import java.util.List;

//இயல்-13
public class KuralChapterGroup {
    private int id;
    private int sectionId;
    private String name;
    private  String tamilName;
    private String transliteration;
    private List<KuralChapter> kuralChapters;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTamilName() {
        return tamilName;
    }

    public void setTamilName(String tamilName) {
        this.tamilName = tamilName;
    }

    public String getTransliteration() {
        return transliteration;
    }

    public void setTransliteration(String transliteration) {
        this.transliteration = transliteration;
    }

    public List<KuralChapter> getKuralChapters() {
        return kuralChapters;
    }

    public void setKuralChapters(List<KuralChapter> kuralChapters) {
        this.kuralChapters = kuralChapters;
    }
}
