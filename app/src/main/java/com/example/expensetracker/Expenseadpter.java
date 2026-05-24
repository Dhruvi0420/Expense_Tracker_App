package com.example.expensetracker;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.Databasehelper;

import java.util.ArrayList;
public class Expenseadpter extends RecyclerView.Adapter<Expenseadpter.ViewHolder> {
    Context context;
    ArrayList<Expense> list;
    com.example.expensetracker.Databasehelper dbHelper;
    public Expenseadpter(Context context, ArrayList<Expense> list) {
        this.context = context;
        this.list = list;
        dbHelper = new Databasehelper(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expense_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = list.get(position);

        holder.title.setText(expense.getTitle());
// Amount Show
        holder.amount.setText("₹ " + expense.getAmount());
// Date Show
        holder.Date.setText(expense.getDate());
// Long Press Delete
        holder.itemView.setOnLongClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            new AlertDialog.Builder(context)
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Expense deleteExpense = list.get(currentPosition);
// Delete From Database
                            dbHelper.deleteExpense(deleteExpense.getId());
// Delete From List
                            list.remove(currentPosition);
// Refresh RecyclerView
                            notifyDataSetChanged();
                            Toast.makeText(context, "Expense Deleted", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, Date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            amount = itemView.findViewById(R.id.tvAmount);
            Date = itemView.findViewById(R.id.tvDate);
        }
    }
}