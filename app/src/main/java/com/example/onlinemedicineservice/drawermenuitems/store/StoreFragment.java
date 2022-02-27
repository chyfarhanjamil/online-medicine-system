package com.example.onlinemedicineservice.drawermenuitems.store;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.onlinemedicineservice.Model.FirebaseProductModel;
import com.example.onlinemedicineservice.ProductDetails;
import com.example.onlinemedicineservice.R;
import com.example.onlinemedicineservice.Adaptar.StoreAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.example.onlinemedicineservice.HomeActivity.PRODUCT_DETAILS_TAG;

public class StoreFragment extends Fragment implements StoreAdapter.onProductClick{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ArrayList<FirebaseProductModel> productList = new ArrayList<FirebaseProductModel>();
    private static ArrayList<FirebaseProductModel> savedProductList = new ArrayList<>();

    public StoreFragment(){}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView  =  inflater.inflate(R.layout.fragment_store, container, false);
        initializeUIComponent(rootView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(savedProductList != null) {
            productList = savedProductList;
            setAdapterForRecycler(productList);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference referenceOfProduct = database.getReference("Products");
        productList = new ArrayList<>();

        referenceOfProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        FirebaseProductModel product = getProductsOnSearch(ds);
                        productList.add(product);
                    }
                }
                setAdapterForRecycler(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {

                    searchTheQuery(s);

                return true;
            }
        });



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedProductList = productList;
    }
    private void initializeUIComponent(View view) {

        recyclerView = view.findViewById(R.id.searchedproduct_recycler_view);
        searchView =  view.findViewById(R.id.searchviewId);


    }

    private void searchTheQuery(String query){

        ArrayList<FirebaseProductModel> myList = new ArrayList<>();
        if(productList != null) {
            for (FirebaseProductModel product : productList) {

                if (product.getProductName().toLowerCase().contains(query.toLowerCase())) {
                    myList.add(product);
                }
            }

            setAdapterForRecycler(myList);
        }


    }

    private void setAdapterForRecycler(List<FirebaseProductModel> productListForAdapter) {
        StoreAdapter adapter = new StoreAdapter(productListForAdapter, getContext(),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private FirebaseProductModel getProductsOnSearch(@NonNull DataSnapshot dataSnapshot) {
        String productName = (String) dataSnapshot.child("productname").getValue();
        String companyName = (String) dataSnapshot.child("companyid").getValue();
        String dosageForm = (String) dataSnapshot.child("dosageform").getValue();
        String strength = (String) dataSnapshot.child("strength").getValue();
        String chemicalFormula = (String) dataSnapshot.child("chemicalformula").getValue();
        String price = (String) dataSnapshot.child("price").getValue();
        String quantity = (String) dataSnapshot.child("quantity").getValue();

        FirebaseProductModel product = new FirebaseProductModel(chemicalFormula,companyName,dosageForm,price,productName,quantity,strength);
        product.setProductId(dataSnapshot.getKey());
        return product;
    }



    @Override
    public void onClickListener(int position, FirebaseProductModel product) {

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host,

                new ProductDetails(product),PRODUCT_DETAILS_TAG).commit();
    }

}
