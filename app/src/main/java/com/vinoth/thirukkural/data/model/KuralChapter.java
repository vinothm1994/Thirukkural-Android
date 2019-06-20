package com.vinoth.thirukkural.data.model;

import java.util.List;

//அதிகாரம் -133
public class KuralChapter {
    private int id;
    private int chapterGroupId;
    private String name;
    private  String tamilName;
    private String transliteration;
    private List<KuralDetail> kuralDetails;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterGroupId() {
        return chapterGroupId;
    }

    public void setChapterGroupId(int chapterGroupId) {
        this.chapterGroupId = chapterGroupId;
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

    public List<KuralDetail> getKuralDetails() {
        return kuralDetails;
    }

    public void setKuralDetails(List<KuralDetail> kuralDetails) {
        this.kuralDetails = kuralDetails;
    }
}
