package com.vinoth.thirukkural.data.model;

//1330
public class KuralDetail {
    private int id;
    private int chapterId;
    private String kuralInTamil;//
    private String kuralInEng;
    private String KuralTransliteration;
    private String engExp;//
    private String mvExp;//
    private String spExp;//
    private String mkExp;//
    private String couplet;//
    private boolean bookmark;

   /* Number: 1,

    Line1: "அகர முதல எழுத்தெல்லாம் ஆதி",
    Line2: "பகவன் முதற்றே உலகு.",

    mv: "எழுத்துக்கள் எல்லாம் அகரத்தை அடிப்படையாக கொண்டிருக்கின்றன. அதுபோல உலகம் கடவுளை அடிப்படையாக கொண்டிருக்கிறது.",
    sp: "எழுத்துக்கள் எல்லாம் அகரத்தில் தொடங்குகின்றன; (அது போல) உலகம் கடவுளில் தொடங்குகிறது.",
    mk: "அகரம் எழுத்துக்களுக்கு முதன்மை; ஆதிபகவன், உலகில் வாழும் உயிர்களுக்கு முதன்மை",
        couplet: "A, as its first of letters, every speech maintains;The "Primal Deity" is first through all the world's domains"
    explanation: "As the letter A is the first of all letters, so the eternal God is first in the world",
missing //    Translation: "'A' leads letters; the Ancient Lord Leads and lords the entire world",
    transliteration1: "Akara Mudhala Ezhuththellaam Aadhi",
    transliteration2: "Pakavan Mudhatre Ulaku",*/


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getKuralInTamil() {
        return kuralInTamil;
    }

    public void setKuralInTamil(String kuralInTamil) {
        this.kuralInTamil = kuralInTamil;
    }

    public String getKuralInEng() {
        return kuralInEng;
    }

    public void setKuralInEng(String kuralInEng) {
        this.kuralInEng = kuralInEng;
    }

    public String getKuralTransliteration() {
        return KuralTransliteration;
    }

    public void setKuralTransliteration(String kuralTransliteration) {
        KuralTransliteration = kuralTransliteration;
    }

    public String getEngExp() {
        return engExp;
    }

    public void setEngExp(String engExp) {
        this.engExp = engExp;
    }

    public String getMvExp() {
        return mvExp;
    }

    public void setMvExp(String mvExp) {
        this.mvExp = mvExp;
    }

    public String getSpExp() {
        return spExp;
    }

    public void setSpExp(String spExp) {
        this.spExp = spExp;
    }

    public String getMkExp() {
        return mkExp;
    }

    public void setMkExp(String mkExp) {
        this.mkExp = mkExp;
    }

    public String getCouplet() {
        return couplet;
    }

    public void setCouplet(String couplet) {
        this.couplet = couplet;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}
