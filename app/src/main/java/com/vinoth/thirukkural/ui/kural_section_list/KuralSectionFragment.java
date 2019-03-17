package com.vinoth.thirukkural.ui.kural_section_list;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class KuralSectionFragment extends Fragment {


    private KuralSectionAdapter kuralSectionAdapter;

    public KuralSectionFragment() {
        // Required empty public constructor
    }
    private KuralScreenListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kural_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView sec_rv = view.findViewById(R.id.sec_rv);
        sec_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        AppDataManager appDataManager = AppDataManager.getInstance(getContext());
        List<KuralSection> kuralSections = appDataManager.getAllkuralSection();
         kuralSectionAdapter = new KuralSectionAdapter(getContext(), kuralSections, listener);
        sec_rv.setAdapter(kuralSectionAdapter);
        sec_rv.addItemDecoration(new DividerItemDecoration(getContext(),RecyclerView.VERTICAL));

    }

}
