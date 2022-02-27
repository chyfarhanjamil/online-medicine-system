package com.example.onlinemedicineservice;

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
import com.example.onlinemedicineservice.Model.FirebaseProductModel;
import com.example.onlinemedicineservice.drawermenuitems.store.StoreFragment;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;
import com.example.onlinemedicineservice.sqlDatabase.DBConnector;

import java.util.Objects;

public class ProductDetails extends Fragment {

    private FirebaseProductModel product;
    private DBConnector connector;
    private int quantity; //this field stores how many product people has added to the cart.. initially it will be 0
    private int availableProduct; //this field stores how many products are available in the stock,

    //UI components
    private TextView productName;
    private TextView productAvailabilityText;
    private TextView productChemicalFormula;
    private TextView productCompanyName;
    private TextView productDosageForm;
    private TextView productStrength;
    private TextView productPrice;
    private TextView quantityTextView;
    private TextView totalPriceTextView;
    private Button addButton;
    private Button plusButton;
    private Button minusButton;
    private String companyName;

    public ProductDetails(@NonNull FirebaseProductModel product){
        this.product = product;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_details, container, false);

        connector = new ViewModelProvider(this).get(DBConnector.class);



        //get all the ui component with their ids
        initializeUIcomponent(rootView);

        availableProduct = Integer.parseInt(product.getQuantity());
        quantity = 0;

        //sets value to all the UI fields
        productName.setText(product.getProductName());
        productCompanyName.setText(product.getCompanyId());
        productDosageForm.setText(product.getDosageForm());
        productStrength.setText(product.getStrength());
        productChemicalFormula.setText(product.getChemicalFormula());
        productPrice.setText(product.getPrice() + " TK");
        if(Integer.parseInt(product.getQuantity()) > 0){
            productAvailabilityText.setText("In Stock");
        }else{
            productAvailabilityText.setText("Out Of Stock");
        }

        //add button listener when add button is pressed
        addButton.setOnClickListener(v -> {

            if(quantity > 0) {
                SQLProductModel SQLProductModel = new SQLProductModel(this.product.getProductId(),
                                                                this.product.getChemicalFormula(),
                                                                this.product.getCompanyId(),
                                                                this.product.getDosageForm(),
                                                                String.valueOf(getProductPrice(quantity)),
                                                                this.product.getProductName(),
                                                                this.product.getStrength(),
                                                                quantity);
                //stores the product in local database
                connector.insertProduct(SQLProductModel);

                Toast.makeText(getContext(), "added", Toast.LENGTH_SHORT).show();

                //after adding the product to the database returns to the storefragment
                Objects.requireNonNull(getActivity()).
                        getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.host, new StoreFragment()).commit();
            }
        });

        //plus button listener when plus button is clicked
        plusButton.setOnClickListener(v -> {
            //if product is availabe only then user can add this to his cart
            if(availableProduct > 0) {
                quantity++;
                String s = "TK "+getProductPrice(quantity);
                totalPriceTextView.setText(s);
                quantityTextView.setText(String.valueOf(quantity));
            }else{
                Toast.makeText(getContext(), "Stock out", Toast.LENGTH_SHORT).show();
            }

        });

        //plus button listener when plus button is clicked
        minusButton.setOnClickListener(v -> {

            if(quantity > 0 ){
                quantity--;
            }
            String s = "TK "+getProductPrice(quantity);
            totalPriceTextView.setText(s);
            quantityTextView.setText(String.valueOf(quantity));
        });

        return rootView;
    }

    private float getProductPrice(int quantity) {

        return  quantity * Float.parseFloat(product.getPrice());
    }

    private void initializeUIcomponent(View rootView){

        productName = rootView.findViewById(R.id.productNameText);
        productAvailabilityText = rootView.findViewById(R.id.productAvailabilityText);
        productChemicalFormula = rootView.findViewById(R.id.chemicalText);
        productCompanyName = rootView.findViewById(R.id.companyNameText);
        productDosageForm = rootView.findViewById(R.id.dosageFormText);
        productStrength = rootView.findViewById(R.id.strengthText);
        productPrice = rootView.findViewById(R.id.priceText);
        addButton = rootView.findViewById(R.id.addbutton);
        plusButton = rootView.findViewById(R.id.plusButton);
        minusButton = rootView.findViewById(R.id.minusButton);
        quantityTextView = rootView.findViewById(R.id.showQuantityText);
        totalPriceTextView = rootView.findViewById(R.id.total_price_textview);

    }

}
