package com.example.newsapp.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> history_list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView History_delete;
        TextView History_content;

        public ViewHolder(View view) {
            super(view);
            History_delete = (ImageView) view.findViewById(R.id.history_delete);
            History_content = (TextView) view.findViewById(R.id.history_content);
        }

    }

    public HistoryAdapter(List<History> history_List) {
        history_list = history_List;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        History history = history_list.get(position);
        holder.History_delete.setImageResource(history.getImageId());
        holder.History_content.setText(history.getName());
    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }
}