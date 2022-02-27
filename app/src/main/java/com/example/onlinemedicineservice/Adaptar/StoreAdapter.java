package com.example.onlinemedicineservice.Adaptar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemedicineservice.Model.FirebaseProductModel;
import com.example.onlinemedicineservice.R;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<FirebaseProductModel> productList;
    private Context context;
    private onProductClick onProductClick;

    public StoreAdapter(List<FirebaseProductModel> productList, Context context, onProductClick onProductClick ){
        this.productList = productList;
        this.context = context;
        this.onProductClick = onProductClick;
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_product,parent,false);
        return new ViewHolder(view,onProductClick);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        holder.setData(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productName;
        TextView productStrength;
        TextView productPrice;
        TextView productAvailability;
        onProductClick onProductClick;
        FirebaseProductModel product;

        ViewHolder(@NonNull View itemView,onProductClick onProductClick) {
            super(itemView);

            productName = itemView.findViewById(R.id.searchedProductName);
            productStrength = itemView.findViewById(R.id.searchedProductStrength);
            productPrice = itemView.findViewById(R.id.searchedProductPrice);
            productAvailability = itemView.findViewById(R.id.searchedProductAvailability);
            this.onProductClick = onProductClick;
            itemView.setOnClickListener(this);

        }

        void setData(FirebaseProductModel product){
            productName.setText(product.getProductName());
            productStrength.setText(product.getStrength());
            productPrice.setText(product.getPrice() + " TK" );
            if(Integer.parseInt(product.getQuantity()) > 0){
                productAvailability.setText("In Stock");
            }else{
                productAvailability.setText("Out Of Stock");
            }
            this.product = product;
        }

        @Override
        public void onClick(View v) {
            onProductClick.onClickListener(getAdapterPosition(),product);
        }
    }

    public interface onProductClick{
        void onClickListener(int position, FirebaseProductModel product);
    }

}
