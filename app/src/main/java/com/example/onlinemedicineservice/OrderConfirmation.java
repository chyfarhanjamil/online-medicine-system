package com.example.onlinemedicineservice;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemedicineservice.Model.FirebaseOrderModel;
import com.example.onlinemedicineservice.Model.FirebaseUserModel;
import com.example.onlinemedicineservice.drawermenuitems.store.StoreFragment;
import com.example.onlinemedicineservice.sqlDatabase.DBConnector;
import com.example.onlinemedicineservice.sqlDatabase.SQLOrderModel;
import com.example.onlinemedicineservice.sqlDatabase.SQLProductModel;
import com.example.onlinemedicineservice.Adaptar.CartAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class OrderConfirmation extends Fragment implements LocationListener {

    List<SQLProductModel> productList;
    float subTotalCost = 0;
    float totalCost = 0;
    private LocationManager mLocationManager;
    TextInputEditText addressText;
    TextInputEditText transactionIDText;
    TextInputEditText transactionNumberText;
    LinearLayout transactionLayout;
    FirebaseUserModel user;
    String currentAddress;
    boolean open = false;
    DBConnector connector;


    public OrderConfirmation(@NonNull List<SQLProductModel> productList) {
        this.productList = productList;

    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                sb.append(address.getAddressLine(i)).append("\n");
            }
            sb.append(address.getSubThoroughfare()).append("\n");
            sb.append(address.getThoroughfare()).append("\n");
            sb.append(address.getLocality()).append("\n");
            sb.append(address.getPostalCode()).append("\n");
            sb.append(address.getCountryName());

            currentAddress = sb.toString();
            addressText.setText(currentAddress);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    interface FetchUser {
        void onCallBack(FirebaseUserModel user);
    }

    interface FetchDelivaryFee{
        void onCallBack(String fee);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_confirmation, container, false);
        connector = new ViewModelProvider(this).get(DBConnector.class);

        RecyclerView recyclerView = rootView.findViewById(R.id.ProductrecycleView);
        TextView subTotalPriceText = rootView.findViewById(R.id.SubTotalPriceText);
        TextView deliveryFeeText = rootView.findViewById(R.id.DeliveryFeeText);
        TextView totalPriceText = rootView.findViewById(R.id.TotalPriceText);
        TextInputEditText phoneNumberText = rootView.findViewById(R.id.PhoneNumberText);
        addressText = rootView.findViewById(R.id.AddressText);
        RadioGroup paymentRadioGroup = rootView.findViewById(R.id.PaymentRadioGroup);
        RadioButton bkashRadioButton = rootView.findViewById(R.id.BkashRadioButton);
        RadioButton cashRadioButton = rootView.findViewById(R.id.CashRadioButton);
        TextView paymentText = rootView.findViewById(R.id.PaymentText);
        Button orderPlaceButton = rootView.findViewById(R.id.OrderPlaceButton);
        transactionIDText = rootView.findViewById(R.id.transactionidID);
        transactionNumberText = rootView.findViewById(R.id.transactionNumberID);
        transactionLayout = rootView.findViewById(R.id.transactionLayout);

        transactionLayout.setPivotY(0);
        transactionLayout.setPivotX((transactionLayout.getWidth() / 2));
        transactionLayout.setVisibility(View.GONE);

        for (SQLProductModel product : productList) {
            subTotalCost += Float.parseFloat(product.getPrice());
        }

        getDelivaryFee(fee -> {
            subTotalPriceText.setText(  String.valueOf(subTotalCost) );
            deliveryFeeText.setText( String.valueOf(Float.parseFloat(fee)) );
            totalCost = subTotalCost+Float.parseFloat(fee);
            totalPriceText.setText(String.valueOf(totalCost));
        });


        getUserInstance(user -> {
            this.user = user;
            phoneNumberText.setText(user.getPhoneNumber());
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CartAdapter adapter = new CartAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (productList != null) {
            adapter.setProductList(productList);
        }

        orderPlaceButton.setOnClickListener(view -> {

            List<String> productIDs = new ArrayList<>();
            for (int i = 0; i < productList.size(); i++) {
                productIDs.add(productList.get(i).getProductId());
            }

            DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders");
            String userID = user.getUid();
            String paymentMethod;
            String transactionID, transactionNumber;
            if(open){
                paymentMethod = "Bkash Payment";
                transactionID = transactionIDText.getText().toString();
                transactionNumber = transactionNumberText.getText().toString();
            }else{
                paymentMethod = "Cash Payment";
                transactionID = "";
                transactionNumber = "";
            }

            LocalDateTime time = java.time.LocalDateTime.now();
            DatabaseReference newNode = orderRef.push();
            newNode.setValue(new FirebaseOrderModel(
                            currentAddress,
                            time,
                            userID,
                            user.getFirstName() + " " + user.getLastName(),
                            Objects.requireNonNull(phoneNumberText.getText()).toString(),
                            productIDs,
                            String.valueOf(totalCost),
                            paymentMethod,
                            transactionID,
                            transactionNumber,
                    "order Received"
                            ));

           connector.deleteAllProduct();

           connector.insertOrder(new SQLOrderModel(newNode.getKey(),
                   time.getHour() + " : " + time.getMinute() + " , "+ time.getDayOfMonth()+ " " +time.getMonth().toString()+ ", " +time.getYear(),
                   String.valueOf(totalCost),paymentMethod,"On The Way"
                   ));


           Toast.makeText(getContext(), "Order Received.", Toast.LENGTH_LONG).show();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host,

                    new StoreFragment()).commit();

        });


        paymentRadioGroup.setOnCheckedChangeListener((group, checkedID) -> {
            // Check which radio button was clicked
            switch (checkedID) {
                case R.id.BkashRadioButton:
                    bkashRadioButton.setButtonDrawable(R.drawable.ic_baseline_radio_button_checked_24);
                    cashRadioButton.setButtonDrawable(R.drawable.ic_baseline_radio_button_unchecked_24);
                    if (!open) {
                        transactionLayout.setVisibility(View.VISIBLE);
                        unfoldTransactionLayout();
                        open = true;
                    }
                    paymentText.setText("You have to send money in these numbers : 01########");
                    break;
                case R.id.CashRadioButton:
                    bkashRadioButton.setButtonDrawable(R.drawable.ic_baseline_radio_button_unchecked_24);
                    cashRadioButton.setButtonDrawable(R.drawable.ic_baseline_radio_button_checked_24);
                    if(open){
                        transactionLayout.setVisibility(View.GONE);
                        foldTransactionLayout();
                        open = false;
                    }
                    paymentText.setText("Hand in the right amount of money to the carrier.");

                    break;
            }

        });


        return rootView;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }


    private void getUserInstance(final FetchUser fetchUser) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Users");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fetchUser.onCallBack(dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(FirebaseUserModel.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void getDelivaryFee(final FetchDelivaryFee fetchDelivaryFee){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Delivaryfee");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fetchDelivaryFee.onCallBack(dataSnapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void unfoldTransactionLayout() {
        ValueAnimator va = ValueAnimator.ofInt(180, 0);
        int mDuration = 500; //0.5 seconds
        va.setDuration(mDuration);
        va.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();
            //transactionLayout.setRotationX(value);
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }
        });
        va.start();
    }

    private void foldTransactionLayout() {
        ValueAnimator va = ValueAnimator.ofInt(0, 180);
        int mDuration = 500; //0.5 seconds
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
            }
        });
        va.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        va.start();
    }


}
