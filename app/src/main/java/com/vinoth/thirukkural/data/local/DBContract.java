package com.vinoth.thirukkural.data.local;

public interface DBContract {


    class SectionContract {
        public final static String TABLE_NAME = "section";
        public final static String ID = "id";
        public final static String TAMIL = "tamil";
        public final static String ENGLISH = "english";
        public final static String TRANSLITERATION = "transliteration";
    }

    class ChapterGroupContract {
        public final static String TABLE_NAME = "chapter_group";
        public final static String ID = "id";
        public final static String SECTION_ID = "section_id";
        public final static String TAMIL = "tamil";
        public final static String ENGLISH = "english";
        public final static String TRANSLITERATION = "transliteration";
    }

    class ChapterContract {
        public final static String TABLE_NAME = "chapter";
        public final static String ID = "id";
        public final static String CHAPTER_GROUP_ID = "chapter_group_id";
        public final static String TAMIL = "tamil";
        public final static String ENGLISH = "english";
        public final static String TRANSLITERATION = "transliteration";
    }


    class KuralContract {
        public final static String TABLE_NAME = "kural_detail";
        public final static String ID = "id";
        public final static String CHAPTER_ID = "chapter_id";
        public final static String TAMIL = "tamil";
        public final static String ENGLISH = "english";
        public final static String TRANSLITERATION = "transliteration";
        public final static String MV_EXP = "mv_exp";
        public final static String SP_EXP = "sp_exp";
        public final static String MK_EXP = "mk_exp";
        public final static String COUPLET = "couplet";
        public final static String ENG_EXP = "eng_exp";
        public final static String BOOKMARK = "bookmark";

    }


}
