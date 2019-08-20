package com.vinoth.thirukkural.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralDetail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ShareApp {

    private Context context;
    private File imagePath;

    public ShareApp(Context context) {
        this.context = context;
    }

    public static float dp2px(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }



    private Bitmap getKuralBitmapImage(KuralDetail kuralDetail) {
        int widthPixels = (int) dp2px(400);
        int heightPixels = (int) dp2px(370);
        View customMarkerViewTitle = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.thirukural_share_item, null, false);
        TextView kural_no_tv = customMarkerViewTitle.findViewById(R.id.kural_no);
        TextView kural_title_tv = customMarkerViewTitle.findViewById(R.id.kural_title);
        TextView kural_details_tv = customMarkerViewTitle.findViewById(R.id.kural_details);
        kural_no_tv.setText(context.getText(R.string.thirukkural) + "-" + kuralDetail.getId());
        kural_title_tv.setText(kuralDetail.getKuralInTamil());
        kural_details_tv.setText(kuralDetail.getMkExp());

        customMarkerViewTitle.setLayoutParams(
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        customMarkerViewTitle.getLayoutParams().width = widthPixels;

        customMarkerViewTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthPixels, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightPixels, View.MeasureSpec.UNSPECIFIED);
        customMarkerViewTitle.measure(widthMeasureSpec, heightMeasureSpec);
        customMarkerViewTitle.layout(0, 0, customMarkerViewTitle.getMeasuredWidth(), customMarkerViewTitle.getMeasuredHeight());
        customMarkerViewTitle.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerViewTitle.getMeasuredWidth(), customMarkerViewTitle.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerViewTitle.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerViewTitle.draw(canvas);
        return returnedBitmap;
    }


    private void saveBitmap(Bitmap bitmap) {
        imagePath = new File(context.getCacheDir() + "/share" + System.currentTimeMillis() + ".jpeg");
        FileOutputStream fos;
        try {
            if (imagePath.exists())
                imagePath.delete();
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, fos);
            fos.flush();
            fos.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareIt() {
        Uri mCapturedImageURI = FileProvider.getUriForFile(context,
                context.getPackageName() + ".provider", imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "https://play.google.com/store/apps/details?id=" + context.getPackageName();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Fingetips");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, mCapturedImageURI);

        List<ResolveInfo> resolvedInfoActivities = context.getPackageManager().queryIntentActivities(sharingIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolvedInfoActivities) {
            context.grantUriPermission(ri.activityInfo.packageName, mCapturedImageURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));


    }

    public void shareKural(KuralDetail kuralDetail) {
        saveBitmap(getKuralBitmapImage(kuralDetail));
        shareIt();
    }


}
