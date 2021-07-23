package com.example.costsharing;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public TripAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }


    public class TripViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;

        public TripViewHolder(View itemView) {
            super(itemView);
            nameText  = itemView.findViewById(R.id.tv_trip_name);
        }
    }


    @Override
    public TripViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TripViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(TripContract.TripsTable.COLUMN_TripName));
        holder.nameText.setText(name);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor !=null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}