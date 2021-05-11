package com.and.travelbuddy.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.and.travelbuddy.R;
import com.and.travelbuddy.data.Trip;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private final ArrayList<Trip> tripArrayList;
    final private TripAdapter.OnListItemClickListener onListItemClickListener;


    public TripAdapter(ArrayList<Trip> tripArrayList, TripAdapter.OnListItemClickListener onListItemClickListener) {
        this.tripArrayList = tripArrayList;
        this.onListItemClickListener = onListItemClickListener;
    }

    public TripAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trip_list_item, parent, false);
        return new TripAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(TripAdapter.ViewHolder viewHolder, int position) {
        viewHolder.title.setText(tripArrayList.get(position).getCountry());
    }

    public int getItemCount() {
        return tripArrayList.size();
    }

    public ArrayList<Trip> getTripArrayList() {
        return tripArrayList;
    }

    public interface OnListItemClickListener {
        void onListItemClick(int trip);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.trip_item_text_country);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onListItemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
