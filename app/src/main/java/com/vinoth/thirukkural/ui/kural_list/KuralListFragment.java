package com.vinoth.thirukkural.ui.kural_list;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralAdapter;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;
import com.vinoth.thirukkural.ui.kural_chapter_list.KuralChapterAdapter;

import org.javatuples.Quartet;

import java.util.ArrayList;
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
public class KuralListFragment extends Fragment {


    private int chapterId;
    private KuralAdapter adapter;

    public KuralListFragment() {
        // Required empty public constructor
    }

    private KuralScreenListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }


    public static KuralListFragment getInstance(int chapterGroupId) {
        KuralListFragment fragment = new KuralListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", chapterGroupId);
        fragment.setArguments(bundle);
        return fragment;
    }


    public static KuralListFragment getInstance( List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> kurals) {
        KuralListFragment fragment = new KuralListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", -2);
        bundle.putSerializable("data", new ArrayList<>(kurals));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chapterId = getArguments().getInt("id", -1);

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
        toolbar_tv.setText(R.string.kurals);
        toolbar.setNavigationOnClickListener(v->getActivity().onBackPressed());

        RecyclerView sec_rv = view.findViewById(R.id.sec_rv);
        sec_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        AppDataManager appDataManager = AppDataManager.getInstance();
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list;
        if (chapterId > 0) {
            list = appDataManager.getAllByChapterId(chapterId);
        } else if (chapterId == -2){
            ArrayList<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> kural= (ArrayList<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>>) getArguments().getSerializable("data");
            list = kural;
        }
        else {
            list = appDataManager.getAll();
        }
        adapter=new KuralAdapter(getContext(),list,listener);
        sec_rv.setAdapter(adapter);
        sec_rv.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));

    }

}
