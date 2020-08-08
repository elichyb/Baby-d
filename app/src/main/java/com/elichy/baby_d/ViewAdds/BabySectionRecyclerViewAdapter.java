package com.elichy.baby_d.ViewAdds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.elichy.baby_d.R;

public class BabySectionRecyclerViewAdapter extends RecyclerView.Adapter<BabySectionRecyclerViewAdapter.BabySectionRecyclerViewHolder> {
    private String[] items;
    private int[] colors;
    private OnSectionListner monSectionListner;

    public BabySectionRecyclerViewAdapter(String[] items ,int[] colors, OnSectionListner monSectionListner) {
        this.items = items;
        this.colors = colors;
        this.monSectionListner = monSectionListner;
    }

    @NonNull
    @Override
    public BabySectionRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.baby_view_template, parent, false);
        BabySectionRecyclerViewHolder babySectionRecyclerViewHolder = new BabySectionRecyclerViewHolder(v, monSectionListner);
        return babySectionRecyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BabySectionRecyclerViewHolder holder, int position) {
        holder.sectionTitle.setText(this.items[position]);
        holder.cardLayuout.setBackgroundColor(this.colors[position]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class BabySectionRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView sectionTitle;
        public RelativeLayout cardLayuout;
        public OnSectionListner onSectionListner;

        public BabySectionRecyclerViewHolder(@NonNull View itemView, OnSectionListner onSectionListner) {
            super(itemView);
            cardLayuout = itemView.findViewById(R.id.cardLayuout);
            sectionTitle = itemView.findViewById(R.id.SectionTitle);
            this.onSectionListner = onSectionListner;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onSectionListner.onSectionClicked(getAdapterPosition());
        }
    }

    public interface OnSectionListner {
        void onSectionClicked(int pos);
    }
}
