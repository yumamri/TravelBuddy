package com.and.travelbuddy.ui.document;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Document;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private final ArrayList<Document> documentArrayList;
    final private OnListItemClickListener onListItemClickListener;


    public DocumentAdapter(ArrayList<Document> documentArrayList, OnListItemClickListener onListItemClickListener) {
        this.documentArrayList = documentArrayList;
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.document_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Document document = documentArrayList.get(position);
        viewHolder.title.setText(document.getTitle());
        Picasso.get().load(document.getImage()).fit().centerInside().into(viewHolder.picture);
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

        TextView title;
        ImageView picture;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.document_item_text_title);
            picture = itemView.findViewById(R.id.document_item_image_preview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
