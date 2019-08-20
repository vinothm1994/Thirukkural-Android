package com.vinoth.thirukkural.ui.home;


import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vinoth.thirukkural.MainActivity;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.utils.ShareApp;

import org.javatuples.Quartet;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private AppDataManager appDataManager;
    private KuralAdapter adapter;
    private KuralScreenListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (KuralScreenListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDataManager = AppDataManager.getInstance();
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
        Menu menu = toolbar.getMenu();
        menu.add(1, 1, 1, "Language").setIcon(R.drawable.ic_translate).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, 2, 2, "Share").setIcon(R.drawable.ic_share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(1, 3, 3, "Feedback");
        TextView toolbar_tv = view.findViewById(R.id.toolbar_tv);
        toolbar.setNavigationIcon(null);
        toolbar_tv.setText(R.string.app_name);
        home_rv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> kural = appDataManager.getAll();
        home_rv.addItemDecoration(new DividerItemDecoration(requireContext(), RecyclerView.VERTICAL));
        adapter = new KuralAdapter(getContext(), kural, listener);
        adapter.showHeader = true;
        home_rv.setAdapter(adapter);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                languagePickerDialog();
            } else if (item.getItemId() == 2) {
                sendFeedback();
            } else {
                ShareApp shareApp = new ShareApp(requireContext());
                shareApp.shareKural(adapter.getList().get(0).getValue3());
            }
            return true;
        });

    }

    private void sendFeedback() {
        final Intent intent = new Intent(Intent.ACTION_APP_ERROR);
        final ApplicationErrorReport report = new ApplicationErrorReport();
        report.packageName = report.processName = requireContext().getPackageName();
        report.time = System.currentTimeMillis();
        report.type = ApplicationErrorReport.TYPE_NONE;
        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
        final PackageManager pm = requireContext().getPackageManager();
        final List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            for (final ResolveInfo resolveInfo : resolveInfos) {
                final String packageName = resolveInfo.activityInfo.packageName;
                // prefer google play app for sending the feedback:
                if ("com.android.vending".equals(packageName)) {
                    // intent.setPackage(packageName);
                    intent.setClassName(packageName, resolveInfo.activityInfo.name);
                    break;
                }
            }
            startActivity(intent);
        } else {
            startActivity(intent);
            // handle the case of not being able to send feedback
        }
    }

    private void languagePickerDialog() {

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(requireContext());
        alt_bld.setTitle("Select  Language");
        String[] grpname = new String[]{
                "English", "தமிழ்"
        };
        alt_bld.setSingleChoiceItems(grpname, -1, (dialog, item) -> {
            appDataManager.setAppLanguage(item == 0);
            dialog.dismiss();
            requireActivity().finish();
            requireActivity().overridePendingTransition(0,0);
            startActivity(new Intent(requireContext(), MainActivity.class));

        });
        AlertDialog alert = alt_bld.create();
        alert.show();


    }
}
