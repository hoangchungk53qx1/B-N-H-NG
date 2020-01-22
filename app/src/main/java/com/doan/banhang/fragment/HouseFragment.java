package com.doan.banhang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.ProductAdapter;
import com.doan.banhang.model.Product;
import com.doan.banhang.view.CartActivity;
import com.doan.banhang.view.SearchActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class HouseFragment extends Fragment {

    private View container;
    private ImageView imgCart;
    private LinearLayout searchLayout;
    private RecyclerView recOrder;
    private ProductAdapter productAdapter;
    private ArrayList<Product> arrayProduct = new ArrayList<>();

    public HouseFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        container = inflater.inflate(R.layout.fragment_house, viewGroup, false);

        initView();

        return container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createOrderList();
        getArrayOrder();
        addEvents();
    }

    private void addEvents() {
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
    }

    private void getArrayOrder() {
        // Lấy dữ liệu sản phẩm từ database và thêm vào arraylist
        FirebaseDatabase.getInstance().getReference()
                .child("Product")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()){
                            arrayProduct.add(dataSnapshot.getValue(Product.class));
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    // Khởi tạo recyclerview và adapter
    private void createOrderList() {
        recOrder.setHasFixedSize(true);
        recOrder.setLayoutManager(new GridLayoutManager(getActivity(),2, VERTICAL,false));
        productAdapter = new ProductAdapter(arrayProduct);
        recOrder.setAdapter(productAdapter);
    }

    // Ánh xạ
    private void initView() {
        imgCart         = container.findViewById(R.id.imgCart);
        recOrder        = container.findViewById(R.id.recOrder);
        searchLayout    = container.findViewById(R.id.searchLayout);
    }
}
