package com.doan.banhang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.OrderAdapter;
import com.doan.banhang.adapter.ProductAdapter;
import com.doan.banhang.model.Order;
import com.doan.banhang.model.Product;
import com.doan.banhang.utils.AccountUtils;
import com.doan.banhang.view.CreateProductActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class AccountFragment extends Fragment {

    private View container;
    private Button btnAddProduct;
    private TextView txtName;
    private RecyclerView recOrder;
    private ProductAdapter productAdapter;
    private ArrayList<Product> arrayProduct = new ArrayList<>();
    private ArrayList<Order> arrayOrder = new ArrayList<>();
    private OrderAdapter orderAdapter;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        container = inflater.inflate(R.layout.fragment_account, viewGroup, false);

        initView();

        return container;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadUI();
    }
    // Khởi tạo recyclerview và adapter
    private void createProductList() {
        recOrder.setHasFixedSize(true);
        recOrder.setLayoutManager(new GridLayoutManager(getActivity(),2, VERTICAL,false));
        productAdapter = new ProductAdapter(arrayProduct);
        recOrder.setAdapter(productAdapter);
    }

    // Kiểm tra Account hiện tại của admin hay user
    private void loadUI() {
        if (AccountUtils.getInstance().getAccount().getEmail() == null){
            addListUser();
            return;
        }

        if (AccountUtils.getInstance().getAccount().getEmail().equals("xxxxxx@gmail.com")) {
            addListAdmin();
        }else {
            addListUser();
        }
    }

    private void addListAdmin() {
        createProductList();

        // Hiển thị Button thêm sản phẩm
        btnAddProduct.setVisibility(View.VISIBLE);
        // Bắt sự kiện ng dùng click vào button thêm đơn hàng
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateProductActivity.class));
            }
        });

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

    private void addListUser(){
        // Ẩn Button thêm sản phẩm
        btnAddProduct.setVisibility(View.GONE);

        txtName.setText(AccountUtils.getInstance().getAccount().getName());

        createOrderList();

        // Lấy dữ liệu sản phẩm từ database và thêm vào arraylist
        FirebaseDatabase.getInstance().getReference()
                .child("Buying")
                .child(AccountUtils.getInstance().getAccount().getUsername())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()){
                            arrayOrder.add(dataSnapshot.getValue(Order.class));
                            orderAdapter.notifyDataSetChanged();
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
        orderAdapter = new OrderAdapter(arrayOrder);
        recOrder.setAdapter(orderAdapter);
    }

    // Ánh xạ
    private void initView() {
        txtName         = container.findViewById(R.id.txtName);
        recOrder        = container.findViewById(R.id.recOrder);
        btnAddProduct   = container.findViewById(R.id.btnAddProduct);
    }
}
