package com.vinoth.thirukkural;

import android.app.Application;

import com.vinoth.thirukkural.data.local.DbUtils;
import com.vinoth.thirukkural.data.local.KuralDbHelper;

import java.io.IOException;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DbUtils.copyKuralDb(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        KuralDbHelper.intiDb(this);
        KuralDbHelper.getInstance().getReadableDatabase();
    }
}
