package com.and.travelbuddy.ui.trip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Checklist;

import java.util.ArrayList;

public class TripChecklistAdapter extends RecyclerView.Adapter<TripChecklistAdapter.ViewHolder> {
    private final ArrayList<Checklist> checklistArrayList;
    private ChecklistListener checklistListener;

    public TripChecklistAdapter(ArrayList<Checklist> checklistArrayList, ChecklistListener checklistListener) {
        this.checklistArrayList = checklistArrayList;
        this.checklistListener = checklistListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_checklist_list_item, parent, false);
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

    public interface ChecklistListener {
        public void handleCheckChanged(Checklist checklist, Boolean isChecked);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.trip_fragment_checklist_checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Checklist checklist = checklistArrayList.get(getAdapterPosition());
                    checklistListener.handleCheckChanged(checklist, isChecked);
                }
            });
        }
    }
}
