package com.example.onlinemedicineservice.Adaptar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.sqlDatabase.SQLOrderModel;


import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<SQLOrderModel> SQLOrderModel;
    private Context context;

    public HistoryAdapter( Context context) {
        this.context = context;
    }

    public void setProductList(List<SQLOrderModel> SQLOrderModel){
        this.SQLOrderModel = SQLOrderModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_entities,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(SQLOrderModel.get(position),position+1);
    }

    @Override
    public int getItemCount() {
        if(SQLOrderModel == null){
            return 0;
        }
        return SQLOrderModel.size();
    }

    public SQLOrderModel getItemAt(int position){
        return SQLOrderModel.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView orderID;
        TextView orderTime;
        TextView orderStatus;
        TextView orderCost;
        TextView paymentMethod;
        TextView orderNumber;

        ViewHolder(@NonNull View view) {
            super(view);

            orderCost = view.findViewById(R.id.amountText);
            orderID = view.findViewById(R.id.OrderIDText);
            orderStatus = view.findViewById(R.id.OrderStatusText);
            orderTime = view.findViewById(R.id.OrderTimeText);
            orderNumber = view.findViewById(R.id.orderNumberText);
            paymentMethod = view.findViewById(R.id.methodtext);

        }

        void setData(final SQLOrderModel SQLOrderModel,int position){

            orderNumber.setText("Order Number "+ position);

            if(SQLOrderModel != null) {
                orderID.setText(SQLOrderModel.getOrderID());
                orderTime.setText(SQLOrderModel.getOrderTime());
                orderStatus.setText(SQLOrderModel.getStatus());
                orderCost.setText(SQLOrderModel.getOrderCost() + " TK");
                paymentMethod.setText(SQLOrderModel.getPaymentMethod());
            }

        }

    }

}

