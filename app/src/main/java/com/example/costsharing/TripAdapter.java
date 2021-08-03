package com.example.costsharing;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener listener;

    public TripAdapter(Context context, Cursor cursor, OnItemClickListener listener) {
        this.mContext = context;
        this.mCursor = cursor;
        this.listener = listener;
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public LinearLayout llNameText;

        public TripViewHolder(View itemView) {
            super(itemView);
            nameText  = itemView.findViewById(R.id.tv_trip_name);
            llNameText = itemView.findViewById(R.id.ll_trip_name);
        }
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TripViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(CostSharingContract.TripsTable.COLUMN_TripName));
        long nameID = mCursor.getLong(mCursor.getColumnIndex(CostSharingContract.TripsTable._ID));
        holder.nameText.setText(name);
        holder.llNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(nameID);
            }
        });
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

    public interface OnItemClickListener {
        void onItemClick(long id);
    }
}