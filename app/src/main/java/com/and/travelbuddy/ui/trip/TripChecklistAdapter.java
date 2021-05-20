package com.and.travelbuddy.ui.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Checklist;

import java.util.ArrayList;

public class TripChecklistAdapter extends RecyclerView.Adapter<TripChecklistAdapter.ViewHolder> {
    private final ArrayList<Checklist> checklistArrayList;
    final private OnListItemClickListener onListItemClickListener;


    public TripChecklistAdapter(ArrayList<Checklist> checklistArrayList, OnListItemClickListener onListItemClickListener) {
        this.checklistArrayList = checklistArrayList;
        this.onListItemClickListener = onListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_trip_checklist_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Checklist checklist = checklistArrayList.get(position);
        viewHolder.checkBox.setText(checklist.getItem());
        viewHolder.checkBox.setChecked(checklist.getaBoolean());
    }

    @Override
    public int getItemCount() {
        return checklistArrayList.size();
    }

    public ArrayList<Checklist> getChecklistArrayList() {
        return checklistArrayList;
    }

    public interface OnListItemClickListener {
        void onListItemClick(int checklist);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.trip_fragment_checklist_checkbox);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
