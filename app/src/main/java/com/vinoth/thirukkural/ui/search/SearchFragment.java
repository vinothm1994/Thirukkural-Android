package com.vinoth.thirukkural.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.vinoth.thirukkural.MainActivity;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import org.javatuples.Quartet;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class SearchFragment extends Fragment {

    private Spinner section_sp;
    private Spinner chapter_group_sp;
    private Spinner chapter_sp;
    private int mSectionId = 0;
    private int mChapterGroupId = 0;
    private int mChapterId = 0;
    private KuralScreenListener listener;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_tv = view.findViewById(R.id.toolbar_tv);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment.this.requireActivity().onBackPressed();
            }
        });
        toolbar_tv.setText(R.string.search);
        section_sp = view.findViewById(R.id.section_sp);
        chapter_group_sp = view.findViewById(R.id.chapter_group_sp);
        chapter_sp = view.findViewById(R.id.chapter_sp);

        setSectionUi();
        setChapGroupUi();
        setChapUi();

        section_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KuralSection item = (KuralSection) parent.getItemAtPosition(position);
                mSectionId = item.getId();
                setChapGroupUi();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chapter_group_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KuralChapterGroup item = (KuralChapterGroup) parent.getItemAtPosition(position);
                mChapterGroupId = item.getId();
                setChapUi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chapter_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                KuralChapter item = (KuralChapter) parent.getItemAtPosition(position);
                mChapterId = item.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText search_et = view.findViewById(R.id.search_et);

        view.findViewById(R.id.search_btn).setOnClickListener((v) -> {
            List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list = AppDataManager.getInstance().searchKural(mSectionId, mChapterGroupId, mChapterId, search_et.getText().toString());

            if (list.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.no_search_result), Toast.LENGTH_SHORT).show();

            } else {
                MainActivity mainActivity = (MainActivity) requireActivity();
                mainActivity.launchSearchKuralList(list);
            }

        });
    }


    private void setSectionUi() {
        List<KuralSection> sections = AppDataManager.getInstance().getAllkuralSection();
        KuralSection firstSection = new KuralSection();
        firstSection.setName(getString(R.string.select_section));
        firstSection.setTamilName(getString(R.string.select_section));
        sections.add(0, firstSection);
        ArrayAdapter<KuralSection> adapter = new ArrayAdapter<KuralSection>(requireContext(), android.R.layout.simple_dropdown_item_1line, sections) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }
        };
        section_sp.setAdapter(adapter);


    }

    private void setChapGroupUi() {
        List<KuralChapterGroup> sections = AppDataManager.getInstance().getAllChapterGroupsBySecId(mSectionId);
        chapter_group_sp.setEnabled(mSectionId != 0);
        KuralChapterGroup kuralChapterGroup = new KuralChapterGroup();
        kuralChapterGroup.setName(getString(R.string.select_chapter_group));
        kuralChapterGroup.setTamilName(getString(R.string.select_chapter_group));
        sections.add(0, kuralChapterGroup);

        ArrayAdapter<KuralChapterGroup> adapter = new ArrayAdapter<KuralChapterGroup>(requireContext(), android.R.layout.simple_dropdown_item_1line, sections) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }
        };
        chapter_group_sp.setAdapter(adapter);

    }


    private void setChapUi() {
        List<KuralChapter> kuralChapters = AppDataManager.getInstance().getAllChapterByGroupId(mChapterGroupId);
        chapter_sp.setEnabled(mChapterGroupId != 0);
        KuralChapter element = new KuralChapter();
        element.setName(getString(R.string.select_chapter));
        element.setTamilName(getString(R.string.select_chapter));
        kuralChapters.add(0, element);
        ArrayAdapter<KuralChapter> adapter = new ArrayAdapter<KuralChapter>(requireContext(), android.R.layout.simple_dropdown_item_1line, kuralChapters) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                view.setText(getItem(position).getTamilName());
                return view;
            }
        };
        chapter_sp.setAdapter(adapter);

    }

}
