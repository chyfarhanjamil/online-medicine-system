package com.example.onlinemedicineservice.drawermenuitems.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemedicineservice.OrderConfirmation;
import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;
import com.example.onlinemedicineservice.sqlDatabase.DBConnector;
import com.example.onlinemedicineservice.Adaptar.CartAdapter;

import java.util.List;

import static com.example.onlinemedicineservice.HomeActivity.ORDER_CONFIRMATION_TAG;

public class CartFragment extends Fragment{

    private RecyclerView recyclerView;
    private Button confirmButton;
    private DBConnector connector;
    private TextView emptyCartText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        connector = new ViewModelProvider(this).get(DBConnector.class);
        confirmButton = root.findViewById(R.id.CONFIRM_BUTTON);
        recyclerView = root.findViewById(R.id.searchedproduct_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyCartText = root.findViewById(R.id.availableText);

        CartAdapter adapter = new CartAdapter(getContext());
        recyclerView.setAdapter(adapter);

        List<SQLProductModel> SQLProductModelList = connector.getAllProduct();

        if(SQLProductModelList != null){
            adapter.setProductList(SQLProductModelList);
        }else{
            Toast.makeText(getContext(), "Empty!", Toast.LENGTH_SHORT).show();
        }

        if(adapter.getItemCount() == 0){
            emptyCartText.setText("Your Cart is Empty!!");
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Toast.makeText(getContext(), "no text", Toast.LENGTH_SHORT).show();

                SQLProductModel product = SQLProductModelList.get(viewHolder.getAdapterPosition());
                SQLProductModelList.remove(viewHolder.getAdapterPosition());
                connector.deleteProduct(product);
                if(SQLProductModelList != null) {
                    recyclerView.setAdapter(adapter);
                    adapter.setProductList(SQLProductModelList);
                }
            }
        }).attachToRecyclerView(recyclerView);

        confirmButton.setOnClickListener(v -> {


            if(adapter.getItemCount() == 0){
                Toast.makeText(getContext(), "No Item in The Cart!", Toast.LENGTH_LONG).show();
            }else {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host,

                        new OrderConfirmation(SQLProductModelList), ORDER_CONFIRMATION_TAG).commit();
            }

        });


        return root;
    }




}
