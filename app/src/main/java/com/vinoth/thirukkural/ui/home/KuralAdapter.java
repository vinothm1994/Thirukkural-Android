package com.vinoth.thirukkural.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.vinoth.thirukkural.R;
import com.vinoth.thirukkural.data.model.KuralChapter;
import com.vinoth.thirukkural.data.model.KuralChapterGroup;
import com.vinoth.thirukkural.data.model.KuralDetail;
import com.vinoth.thirukkural.data.model.KuralSection;

import org.javatuples.Quartet;

import java.util.List;

public class KuralAdapter extends RecyclerView.Adapter {

    private static final String TAG = KuralAdapter.class.getSimpleName();
    public boolean showHeader = false;
    private Context context;
    private List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list;
    private KuralScreenListener onItemClickListener;

    public KuralAdapter(Context context, List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> list,
                        KuralScreenListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public List<Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail>> getList() {
        return list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == 0) {
            View view = inflater.inflate(R.layout.kural_home_header, parent, false);
            HeaderViewHolder headerViewHolder = new HeaderViewHolder(view);
            return headerViewHolder;
        } else {
            View view = inflater.inflate(R.layout.kural_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            int tempPos = showHeader ? position - 1 : position;
            Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail> item = list.get(tempPos);
            ViewHolder vh = (ViewHolder) holder;
            vh.bind(item);
        } else {

        }


    }

    @Override
    public int getItemCount() {
        if (showHeader)
            return list.size() + 1;
        else
            return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showHeader && position == 0) {
            return 0;
        } else
            return 1;

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.findViewById(R.id.search_tv).setOnClickListener(this);
            itemView.findViewById(R.id.section_btn).setOnClickListener(this);
            itemView.findViewById(R.id.chap_gr_btn).setOnClickListener(this);
            itemView.findViewById(R.id.chap_btn).setOnClickListener(this);
            itemView.findViewById(R.id.kural_btn).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.search_tv:
                    onItemClickListener.launchSearch();
                    break;
                case R.id.section_btn:
                    onItemClickListener.launchSection();
                    break;
                case R.id.chap_gr_btn:
                    onItemClickListener.launchChapterGroup();
                    break;
                case R.id.chap_btn:
                    onItemClickListener.launchChapter();
                    break;
                case R.id.kural_btn:
                    onItemClickListener.launchAllKuralList();
                    break;
            }


        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView kural_txt;
        private final Chip chip1, chip2, chip3, chip4;

        public ViewHolder(View itemView) {
            super(itemView);
            kural_txt = itemView.findViewById(R.id.kural_txt);
            chip1 = itemView.findViewById(R.id.chip1);
            chip2 = itemView.findViewById(R.id.chip2);
            chip3 = itemView.findViewById(R.id.chip3);
            chip4 = itemView.findViewById(R.id.chip4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = showHeader ? getLayoutPosition() - 1 : getLayoutPosition();
                    onItemClickListener.launchKuralDetail(list.get(pos).getValue3().getId());
                }
            });
            View.OnClickListener listener = v -> {
                int id = v.getId();
                int pos = showHeader ? getLayoutPosition() - 1 : getLayoutPosition();
                Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail> item =
                        list.get(pos);
                if (id == R.id.chip2) {
                    onItemClickListener.launchChapterGroup(item.getValue0().getId());
                } else if (id == R.id.chip3) {
                    onItemClickListener.launchChapter(item.getValue1().getId());
                } else if (id == R.id.chip4) {
                    onItemClickListener.launchKuralList(item.getValue2().getId());
                }


            };
            chip2.setOnClickListener(listener);
            chip3.setOnClickListener(listener);
            chip4.setOnClickListener(listener);

        }

        public void bind(Quartet<KuralSection, KuralChapterGroup, KuralChapter, KuralDetail> item) {
            kural_txt.setText(item.getValue3().getKuralInTamil());
            chip1.setText(super.itemView.getContext().getText(R.string.kural) + " " + item.getValue3().getId());
            chip2.setText(item.getValue0().getTamilName());
            chip3.setText(item.getValue1().getTamilName());
            chip4.setText(item.getValue2().getTamilName());
        }
    }

}