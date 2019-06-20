package com.vinoth.thirukkural.data.model;

import java.util.List;

//பால் -3
public class KuralSection {
    private int id;
    private String name;
    private String tamilName;
    private String transliteration;
    private List<KuralChapterGroup> kuralChapterGroups;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<KuralChapterGroup> getKuralChapterGroups() {
        return kuralChapterGroups;
    }

    public void setKuralChapterGroups(List<KuralChapterGroup> kuralChapterGroups) {
        this.kuralChapterGroups = kuralChapterGroups;
    }
}


