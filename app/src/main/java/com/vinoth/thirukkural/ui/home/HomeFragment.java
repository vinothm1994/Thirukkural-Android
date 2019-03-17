package com.vinoth.thirukkural.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.kural_detail.KuralDetailFragment;

import org.javatuples.Quartet;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private KuralAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    private  KuralScreenListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kural_section, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView home_rv = view.findViewById(R.id.sec_rv);
        home_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> ll = AppDataManager.getInstance(getContext()).getAll();
        adapter = new KuralAdapter(getContext(), ll,listener);
        home_rv.setAdapter(adapter);
    }
}
