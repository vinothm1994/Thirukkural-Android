package com.vinoth.thirukkural.ui.kural_detail;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class KuralDetailFragment extends Fragment {

    private int id;

    public static KuralDetailFragment getInstance(int id) {
        KuralDetailFragment  fragment=new KuralDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        fragment.setArguments(args);
        return fragment;
    }

    private KuralScreenListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }



    public KuralDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getArguments().getInt("id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kural_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_tv=view.findViewById(R.id.toolbar_tv);
        toolbar_tv.setText("Kural Section");
        toolbar.setNavigationOnClickListener(v->getActivity().onBackPressed());

        ChipGroup chip_gp = view.findViewById(R.id.chip_gp);
        TextView kutal_dtl_tv = view.findViewById(R.id.kutal_dtl_tv);
        TextView kutal_dtl_no_tv = view.findViewById(R.id.kutal_dtl_no_tv);

        AppDataManager appDataManager=new AppDataManager(getContext());
        KuralDetail kuralDetail = appDataManager.getAllKuralDetailsByKuralId(id);
        KuralChapter kuralChapter = appDataManager.getAllChapterById(kuralDetail.getChapterId());
        KuralChapterGroup chapterGroup = appDataManager.getAllChaptergroupsById(kuralChapter.getChapterGroupId());
        KuralSection kuralSection = appDataManager.getAllkuralSection(chapterGroup.getSectionId());
        kutal_dtl_tv.setText(kuralDetail.getKuralInTamil());

        kutal_dtl_no_tv.setText("kural-"+id);


        Chip chip=new Chip(getContext());
        chip.setText("Kural-"+id);
        chip_gp.addView(chip);

        chip=new Chip(getContext());
        chip.setText(kuralChapter.getTamilName());
        chip_gp.addView(chip);


        chip=new Chip(getContext());
        chip.setText(chapterGroup.getTamilName());
        chip_gp.addView(chip);

        chip=new Chip(getContext());
        chip.setText(kuralSection.getTamilName());
        chip_gp.addView(chip);

        TextView mv_txt=view.findViewById(R.id.mv_txt);
        mv_txt.setText(kuralDetail.getMvExp());

        TextView mk_txt=view.findViewById(R.id.mk_txt);
        mk_txt.setText(kuralDetail.getMkExp());

        TextView sp_txt=view.findViewById(R.id.sp_txt);
        sp_txt.setText(kuralDetail.getSpExp());




    }
}
