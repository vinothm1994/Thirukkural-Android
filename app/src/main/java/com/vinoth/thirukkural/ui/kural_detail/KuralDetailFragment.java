package com.vinoth.thirukkural.ui.kural_detail;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;
import com.vinoth.thirukkural.utils.ShareApp;

/**
 * A simple {@link Fragment} subclass.
 */
public class KuralDetailFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton bookmark_fab;
    private int id;
    private KuralDetail kuralDetail;
    private KuralScreenListener listener;

    public KuralDetailFragment() {
        // Required empty public constructor
    }

    public static KuralDetailFragment getInstance(int id) {
        KuralDetailFragment fragment = new KuralDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kural_detail, container, false);
    }

    void share() {
        ShareApp sh = new ShareApp(requireContext());
        sh.saveBitmap(sh.getShareLeaderBoard(kuralDetail));
        sh.shareIt();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_tv = view.findViewById(R.id.toolbar_tv);
        bookmark_fab = view.findViewById(R.id.bookmark_fab);
        BottomAppBar bottom_bar = view.findViewById(R.id.bottom_bar);
        MenuItem menuItem = bottom_bar.getMenu().add("Share");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.ic_share);
        bottom_bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                share();
                return true;
            }
        });

        toolbar_tv.setText(R.string.kural_detail);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());

        ChipGroup chip_gp = view.findViewById(R.id.chip_gp);
        TextView kutal_dtl_tv = view.findViewById(R.id.kutal_dtl_tv);

        AppDataManager appDataManager = new AppDataManager(getContext());
        kuralDetail = appDataManager.getAllKuralDetailsByKuralId(id);
        KuralChapter kuralChapter = appDataManager.getAllChapterById(kuralDetail.getChapterId());
        KuralChapterGroup chapterGroup = appDataManager.getAllChaptergroupsById(kuralChapter.getChapterGroupId());
        KuralSection kuralSection = appDataManager.getAllkuralSection(chapterGroup.getSectionId());
        kutal_dtl_tv.setText(kuralDetail.getKuralInTamil());


        Chip chip = new Chip(getContext());
        chip.setText(getString(R.string.kural) + "-" + id);
        chip_gp.addView(chip);

        chip = new Chip(getContext());
        chip.setText(kuralChapter.getTamilName());
        chip_gp.addView(chip);


        chip = new Chip(getContext());
        chip.setText(chapterGroup.getTamilName());
        chip_gp.addView(chip);

        chip = new Chip(getContext());
        chip.setText(kuralSection.getTamilName());
        chip_gp.addView(chip);

        TextView mv_txt = view.findViewById(R.id.mv_txt);
        mv_txt.setText(kuralDetail.getMvExp());

        TextView mk_txt = view.findViewById(R.id.mk_txt);
        mk_txt.setText(kuralDetail.getMkExp());

        TextView sp_txt = view.findViewById(R.id.sp_txt);
        sp_txt.setText(kuralDetail.getSpExp());

        TextView eng_txt = view.findViewById(R.id.eng_txt);
        eng_txt.setText(kuralDetail.getEngExp());

        TextView tranlit_txt = view.findViewById(R.id.tranlit_txt);
        tranlit_txt.setText(kuralDetail.getKuralTransliteration());
        bookmark_fab.setOnClickListener(this);

        setBookmarkUi();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bookmark_fab) {
            setBookmark();
        }

    }


    private void setBookmarkUi() {
        bookmark_fab.setImageResource(kuralDetail.isBookmark() ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_border);
    }

    private void setBookmark() {
        kuralDetail.setBookmark(!kuralDetail.isBookmark());
        setBookmarkUi();
        AppDataManager.getInstance().updateBookmark(kuralDetail.getId(), kuralDetail.isBookmark());

    }
}
