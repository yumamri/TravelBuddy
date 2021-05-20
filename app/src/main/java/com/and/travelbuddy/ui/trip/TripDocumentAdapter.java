package com.and.travelbuddy.ui.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;

import java.util.ArrayList;

public class TripDocumentAdapter extends RecyclerView.Adapter<TripDocumentAdapter.ViewHolder> {
    private final ArrayList<Document> documentArrayList;
    private final OnListItemClickListener onListItemClickListener;


    public TripDocumentAdapter(ArrayList<Document> documentArrayList, OnListItemClickListener onListItemClickListener) {
        this.documentArrayList = documentArrayList;
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_document_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Document document = documentArrayList.get(position);
        viewHolder.title.setText(document.getTitle());
        viewHolder.category.setText(document.getCategory());
    }

    @Override
    public int getItemCount() {
        return documentArrayList.size();
    }

    public ArrayList<Document> getDocumentArrayList() {
        return documentArrayList;
    }

    public interface OnListItemClickListener {
        void onListItemClick(int document);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView category;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trip_document_fragment_text_name);
            category = itemView.findViewById(R.id.trip_document_fragment_text_category);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
