package com.example.costsharing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpenseViewHolder> {

    private final List<Expense> expensesList = new ArrayList<>();

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

         final TextView value;
         final TextView name;

        public ExpenseViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            name = (TextView) view.findViewById(R.id.name);
            value = (TextView) view.findViewById(R.id.value);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_expense, viewGroup, false);
        return new ExpenseViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExpenseViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.name.setText(expensesList.get(position).getName());
        viewHolder.value.setText(Double.toString(expensesList.get(position).getValue()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public void setExpenses(List<Expense> newExpenses ){
        this.expensesList.clear();
        this.expensesList.addAll(newExpenses);
        notifyDataSetChanged();
    }

}
