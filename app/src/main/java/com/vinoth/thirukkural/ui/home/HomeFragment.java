package com.vinoth.thirukkural.ui.home;


import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import com.vinoth.thirukkural.ui.kural_detail.KuralDetailFragment;

import org.javatuples.Quartet;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView home_rv = view.findViewById(R.id.sec_rv);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_tv=view.findViewById(R.id.toolbar_tv);
        toolbar.setNavigationIcon(null);
        toolbar_tv.setText(R.string.app_name);
        home_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> ll = AppDataManager.getInstance(requireContext()).getAll();
        home_rv.addItemDecoration(new DividerItemDecoration(requireContext(),RecyclerView.VERTICAL));
        adapter = new KuralAdapter(getContext(), ll,listener);
        adapter.showHeader=true;
        home_rv.setAdapter(adapter);
    }
}
