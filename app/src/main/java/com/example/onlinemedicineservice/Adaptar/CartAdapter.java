package com.example.onlinemedicineservice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<SQLProductModel> SQLProductModel;
    private Context context;

    public CartAdapter( Context context) {
        this.context = context;
    }

    public void setProductList(List<SQLProductModel> SQLProductModel){
        this.SQLProductModel = SQLProductModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(SQLProductModel.get(position));
    }

    @Override
    public int getItemCount() {
        if(SQLProductModel == null){
            return 0;
        }
        return SQLProductModel.size();
    }

    public SQLProductModel getItemAt(int position){
        return SQLProductModel.get(position);
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView productStrength;
        TextView productPrice;
        TextView productQuantity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.searchedProductName);
            productStrength = itemView.findViewById(R.id.searchedProductStrength);
            productPrice = itemView.findViewById(R.id.cart_product_price);
            productQuantity = itemView.findViewById(R.id.cart_product_quantity);

        }

        void setData(final SQLProductModel SQLProductModel){

            productName.setText(SQLProductModel.getProductName());
            productStrength.setText(SQLProductModel.getStrength());
            productPrice.setText(SQLProductModel.getPrice() + " TK");
            productQuantity.setText(String.valueOf(SQLProductModel.getSelectedQuantity()) + " Pieces");
        }

    }

}
