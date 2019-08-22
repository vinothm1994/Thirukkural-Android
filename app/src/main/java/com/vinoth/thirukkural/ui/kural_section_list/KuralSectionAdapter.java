package com.vinoth.thirukkural.ui.kural_section_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralSection;
import com.vinoth.thirukkural.ui.home.KuralScreenListener;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static com.vinoth.thirukkural.data.AppDataManager.isEnglish;

public class KuralSectionAdapter extends
        RecyclerView.Adapter<KuralSectionAdapter.ViewHolder> {

    private static final String TAG = KuralSectionAdapter.class.getSimpleName();

    private Context context;
    private List<KuralSection> list;
    private KuralScreenListener onItemClickListener;

    public KuralSectionAdapter(Context context, List<KuralSection> list,
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
        KuralSection item = list.get(position);
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
                    onItemClickListener.launchChapterGroup(list.get(getLayoutPosition()).getId());
                }
            });
        }

        public void bind(final KuralSection model) {
            sec_tv.setText(model.getId()+"");
            sec_title_tv.setText(isEnglish?model.getName():model.getTamilName());

        }
    }

}