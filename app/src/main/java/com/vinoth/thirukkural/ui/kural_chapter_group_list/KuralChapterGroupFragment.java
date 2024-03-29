package com.vinoth.thirukkural.ui.kural_chapter_group_list;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;
import com.vinoth.thirukkural.ui.kural_section_list.KuralSectionAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KuralChapterGroupFragment extends Fragment {


    private int secId;

    public KuralChapterGroupFragment() {
        // Required empty public constructor
    }
    private KuralScreenListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }


    public static KuralChapterGroupFragment getInstance(int secId) {
        KuralChapterGroupFragment fragment = new KuralChapterGroupFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", secId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secId = getArguments().getInt("id", -1);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kural_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_tv=view.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText(R.string.chapter_groups);
        toolbar.setNavigationOnClickListener(v->getActivity().onBackPressed());

        RecyclerView sec_rv = view.findViewById(R.id.sec_rv);
        sec_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        AppDataManager appDataManager = AppDataManager.getInstance();

        List<KuralChapterGroup> kuralChapterGroups;
        if (secId > 0)
            kuralChapterGroups = appDataManager.getAllChapterGroupsBySecId(secId);
        else
            kuralChapterGroups = appDataManager.getAllChaptergroups();
        KuralChapterGroupAdapter kuralSectionAdapter = new KuralChapterGroupAdapter(getContext(), kuralChapterGroups, listener);
        sec_rv.setAdapter(kuralSectionAdapter);
        sec_rv.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));

    }

}
