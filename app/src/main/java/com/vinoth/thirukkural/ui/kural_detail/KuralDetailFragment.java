package com.vinoth.thirukkural.ui.kural_detail;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vinoth.thirukkural.R;

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

}
