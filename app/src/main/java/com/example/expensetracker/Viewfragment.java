package com.example.expensetracker;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expensetracker.Databasehelper;
import com.example.expensetracker.Expense;
import com.example.expensetracker.Expenseadpter;

import java.util.ArrayList;
public class Viewfragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Expense> list;
    Expenseadpter adapter;
    Databasehelper db;
    TextView total;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_view, container, false);
        recyclerView=view.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db=new Databasehelper(getContext());
        total=view.findViewById(R.id.total);
        list=new ArrayList<>();
        Cursor cursor = db.getAllExpense();
        while(cursor.moveToNext()){
            Log.d("text","list");
            list.add(new Expense(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }
        Cursor totalAmountCursor = db.getTotalAmount();
        if(totalAmountCursor!=null){
            if (totalAmountCursor.moveToFirst()){
                String amount = totalAmountCursor.getString(0);
                total.setText("Total: "+amount);
            }
        }
        adapter=new Expenseadpter(getContext(),list);
        recyclerView.setAdapter(adapter);
        return view;
    }
}