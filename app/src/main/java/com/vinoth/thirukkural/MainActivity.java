package com.vinoth.thirukkural;

import android.os.Bundle;

import com.vinoth.thirukkural.data.local.DbUtils;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.HomeFragment;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;
import com.vinoth.thirukkural.ui.kural_chapter_group_list.KuralChapterGroupFragment;
import com.vinoth.thirukkural.ui.kural_chapter_list.KuralChapterFragment;
import com.vinoth.thirukkural.ui.kural_detail.KuralDetailFragment;
import com.vinoth.thirukkural.ui.kural_list.KuralListFragment;
import com.vinoth.thirukkural.ui.kural_section_list.KuralSectionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements KuralScreenListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

    }


    void insert() throws JSONException, IOException {
        JSONObject jsonObject = getJson(R.raw.thirukkural_json);
        JSONArray jsonArray = jsonObject.getJSONArray("kural");
        List<KuralDetail> kuralDetails = new ArrayList<>(jsonArray.length());
        Map<Integer, KuralDetail> detailMap = new HashMap<>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObj = jsonArray.getJSONObject(i);
            KuralDetail kuralDetail = new KuralDetail();
            kuralDetail.setId(jsonObj.getInt("Number"));
            kuralDetail.setChapterId(-1);
            kuralDetail.setKuralInTamil(jsonObj.getString("Line1") + "\n" + jsonObj.getString("Line2"));
            kuralDetail.setKuralTransliteration(jsonObj.getString("transliteration1") + "\n" + jsonObj.getString("transliteration2"));
            kuralDetail.setKuralInEng(jsonObj.getString("Translation"));
            kuralDetail.setMvExp(jsonObj.getString("mv"));
            kuralDetail.setSpExp(jsonObj.getString("sp"));
            kuralDetail.setMkExp(jsonObj.getString("mk"));
            kuralDetail.setCouplet(jsonObj.getString("couplet"));
            kuralDetail.setEngExp(jsonObj.getString("explanation"));
            kuralDetails.add(kuralDetail);
            detailMap.put(kuralDetail.getId(), kuralDetail);
        }

        JSONObject json = getJson(R.raw.kural_detail);
        JSONArray array = json.getJSONObject("section").getJSONArray("detail");
        setSecArray(array, detailMap);


    }

    private void setSecArray(JSONArray secArray, Map<Integer, KuralDetail> kuralDetailHashMap) throws JSONException {
        List<KuralSection> kuralSections = new ArrayList<>();
        for (int i = 0; i < secArray.length(); i++) {
            JSONObject secObject = secArray.getJSONObject(i);
            KuralSection kuralSection = new KuralSection();
            kuralSection.setId(secObject.getInt("number"));
            kuralSection.setName(secObject.getString("translation"));
            kuralSection.setTamilName(secObject.getString("name"));
            kuralSection.setTransliteration(secObject.getString("transliteration"));
            List<KuralChapterGroup> chGrpList = new ArrayList<>();
            kuralSection.setKuralChapterGroups(chGrpList);
            kuralSections.add(kuralSection);
            JSONArray chapGrpArray = secObject.getJSONObject("chapterGroup").getJSONArray("detail");
            for (int j = 0; j < chapGrpArray.length(); j++) {
                JSONObject chGJsonObject = chapGrpArray.getJSONObject(j);
                KuralChapterGroup kuralChaptergroup = new KuralChapterGroup();
                chGrpList.add(kuralChaptergroup);
                kuralChaptergroup.setId(chGJsonObject.getInt("number"));
                kuralChaptergroup.setSectionId(kuralSection.getId());
                kuralChaptergroup.setName(chGJsonObject.getString("translation"));
                kuralChaptergroup.setTamilName(chGJsonObject.getString("name"));
                kuralChaptergroup.setTransliteration(chGJsonObject.getString("transliteration"));
                List<KuralChapter> chList = new ArrayList<>();
                kuralChaptergroup.setKuralChapters(chList);
                JSONArray chpArray = chGJsonObject.getJSONObject("chapters").getJSONArray("detail");
                for (int k = 0; k < chpArray.length(); k++) {
                    JSONObject chpJsonObject = chpArray.getJSONObject(k);
                    KuralChapter kuralChapter = new KuralChapter();
                    chList.add(kuralChapter);
                    kuralChapter.setId(chpJsonObject.getInt("number"));
                    kuralChapter.setChapterGroupId(kuralChaptergroup.getId());
                    kuralChapter.setName(chpJsonObject.getString("translation"));
                    kuralChapter.setTamilName(chpJsonObject.getString("name"));
                    kuralChapter.setTransliteration(chpJsonObject.getString("transliteration"));
                    List<KuralDetail> kuralsList = new ArrayList<>();
                    kuralChapter.setKuralDetails(kuralsList);
                    int start = chpJsonObject.getInt("start");
                    int end = chpJsonObject.getInt("end");
                    for (int l = start; l <= end; l++) {
                        KuralDetail kuralDetail = kuralDetailHashMap.get(l);
                        kuralDetail.setChapterId(kuralChapter.getId());
                        kuralsList.add(kuralDetail);
                    }

                }
            }
        }

        new Thread(() -> {
            DbUtils.insert(kuralSections);
        }).start();


    }


    private JSONObject getJson(int id) throws IOException, JSONException {
        try (InputStream is = getResources().openRawResource(id)) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            String jsonString = writer.toString();
            return new JSONObject(jsonString);
        }


    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment, fragment.getTag())
                .commit();


    }


    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment, fragment.getTag())
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void launchSection() {
        addFragment(new KuralSectionFragment());

    }

    @Override
    public void launchChapterGroup() {
        addFragment(KuralChapterGroupFragment.getInstance(-1));
    }

    @Override
    public void launchChapterGroup(int secId) {
        addFragment(KuralChapterGroupFragment.getInstance(secId));
    }

    @Override
    public void launchChapter() {
        addFragment(KuralChapterFragment.getInstance(-1));
    }

    @Override
    public void launchChapter(int chapterGroupId) {
        addFragment(KuralChapterFragment.getInstance(chapterGroupId));
    }

    @Override
    public void launchKuralList(int chapterId) {
        addFragment(KuralListFragment.getInstance(chapterId));
    }

    @Override
    public void launchAllKuralList() {
        addFragment(KuralListFragment.getInstance(-1));
    }

    @Override
    public void launchKuralDetail(int kuralId) {
        addFragment(KuralDetailFragment.getInstance(kuralId));
    }
}
