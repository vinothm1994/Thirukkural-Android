package com.vinoth.thirukkural.ui.kural_chapter_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class KuralChapterAdapter extends
        RecyclerView.Adapter<KuralChapterAdapter.ViewHolder> {

    private static final String TAG = KuralChapterAdapter.class.getSimpleName();

    private Context context;
    private List<KuralChapter> list;
    private KuralScreenListener onItemClickListener;

    public KuralChapterAdapter(Context context, List<KuralChapter> list,
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
        KuralChapter item = list.get(position);
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
                    onItemClickListener.launchKuralList(list.get(getLayoutPosition()).getId());
                }
            });
        }

        public void bind(final KuralChapter model) {
            sec_tv.setText(model.getId()+"");
            sec_title_tv.setText(model.getTamilName());

        }
    }

}