package com.example.onlinemedicineservice.drawermenuitems.Order_History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemedicineservice.Adaptar.CartAdapter;
import com.example.onlinemedicineservice.Adaptar.HistoryAdapter;
import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.sqlDatabase.DBConnector;
import com.example.onlinemedicineservice.sqlDatabase.SQLOrderModel;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;

import java.util.List;

public class OrderHistoryFragment extends Fragment {

    TextView orderID;
    TextView orderTime;
    TextView orderStatus;
    TextView orderCost;
    TextView paymentMethod;

    DBConnector connector;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_orderhistory, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.orderRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        connector = new ViewModelProvider(this).get(DBConnector.class);

        HistoryAdapter adapter = new HistoryAdapter(getContext());
        recyclerView.setAdapter(adapter);

        List<SQLOrderModel> SQLOrderModelList = connector.getALlOrder();

        if(SQLOrderModelList != null){
            adapter.setProductList(SQLOrderModelList);
        }else{
            Toast.makeText(getContext(), "Empty!", Toast.LENGTH_SHORT).show();
        }



        return view;
    }

}
