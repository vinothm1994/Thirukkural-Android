package com.vinoth.thirukkural.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;

import org.javatuples.Quartet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KuralAdapter extends
        RecyclerView.Adapter<KuralAdapter.ViewHolder> {

    private static final String TAG = KuralAdapter.class.getSimpleName();

    private Context context;
    private List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list;
    private OnItemClickListener onItemClickListener;

    public KuralAdapter(Context context, List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list,
                        OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.kural_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail> item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView kural_txt;
        private final Chip chip1,chip2,chip3,chip4;

        public ViewHolder(View itemView) {
            super(itemView);
            kural_txt = itemView.findViewById(R.id.kural_txt);
            chip1= itemView.findViewById(R.id.chip1);
            chip2= itemView.findViewById(R.id.chip2);
            chip3= itemView.findViewById(R.id.chip3);
            chip4= itemView.findViewById(R.id.chip4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getLayoutPosition());
                }
            });
        }

        public void bind(Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail> item) {
            kural_txt.setText(item.getValue3().getKuralInTamil());
            chip1.setText("kural "+item.getValue3().getId());
            chip2.setText(item.getValue0().getTamilName());
            chip3.setText(item.getValue1().getTamilName());
            chip4.setText(item.getValue2().getTamilName());
        }
    }

}