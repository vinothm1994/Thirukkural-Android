package com.vinoth.thirukkural;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

import com.vinoth.thirukkural.data.AppDataManager;
import com.vinoth.thirukkural.data.local.DbUtils;
import com.vinoth.thirukkural.data.local.KuralDbHelper;

import java.io.IOException;
import java.util.Locale;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            boolean isDbAvailable = DbUtils.copyKuralDb(this);
            Log.i("", "onCreate: " + isDbAvailable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        KuralDbHelper.intiDb(this);
        KuralDbHelper.getInstance().getReadableDatabase();
        AppDataManager.getInstance(this);
    }

}
