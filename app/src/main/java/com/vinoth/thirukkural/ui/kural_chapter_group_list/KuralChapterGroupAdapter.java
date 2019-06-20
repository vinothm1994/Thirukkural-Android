package com.vinoth.thirukkural.ui.kural_chapter_group_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class KuralChapterGroupAdapter extends
        RecyclerView.Adapter<KuralChapterGroupAdapter.ViewHolder> {

    private static final String TAG = KuralChapterGroupAdapter.class.getSimpleName();

    private Context context;
    private List<KuralChapterGroup> list;
    private KuralScreenListener onItemClickListener;




    public KuralChapterGroupAdapter(Context context, List<KuralChapterGroup> list,
                                    KuralScreenListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.kural_section_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KuralChapterGroup item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView sec_tv;
        private final TextView sec_title_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            sec_tv = itemView.findViewById(R.id.sec_tv);
            sec_title_tv = itemView.findViewById(R.id.sec_title_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.launchChapter(list.get(getLayoutPosition()).getId());
                }
            });
        }

        public void bind(final KuralChapterGroup model) {
            sec_tv.setText(model.getId()+"");
            sec_title_tv.setText(model.getTamilName());

        }
    }

}