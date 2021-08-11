package com.example.costsharing;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder> {

    private final Context mContext;
    private Cursor mCursor;
    CostSharingDbHelper dbHelper;

    public ExpensesAdapter(Context context, Cursor cursor, CostSharingDbHelper dbHelper) {
        this.mContext = context;
        this.mCursor = cursor;
        this.dbHelper = dbHelper;
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        public TextView tvValue;
        public TextView tvName;
        public TextView tvPayer;

        public ExpenseViewHolder(View view) {
            super(view);
            tvName = itemView.findViewById(R.id.tv_name);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvPayer = itemView.findViewById(R.id.tv_payer);
        }
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_expense, viewGroup, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, final int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(CostSharingContract.ExpensesTable.COLUMN_ExpName));
        Double expValue = mCursor.getDouble(mCursor.getColumnIndex(CostSharingContract.ExpensesTable.COLUMN_ExpValue));
        String payer = dbHelper.getPayerNameByID(mCursor.getLong(mCursor.getColumnIndex(CostSharingContract.ExpensesTable.COLUMN_PartID)));

        viewHolder.tvName.setText(name);
        viewHolder.tvValue.setText(Double.toString(expValue));
        viewHolder.tvPayer.setText(payer);
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
