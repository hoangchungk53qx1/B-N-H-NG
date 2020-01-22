package com.doan.banhang.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.SearchProductAdapter;
import com.doan.banhang.model.Product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recOrder;
    private EditText    edtSearch;
    private SearchProductAdapter searchProductAdapter;
    private ArrayList<Product> arrayProduct = new ArrayList<>();
    private ArrayList<Product> arrayProductSearch = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        createOrderList();
        getArrayProduct();
        addEvents();
    }

    private void addEvents() {
        // Bắt sự kiện người dùng nhập text vào và search
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    arrayProductSearch.clear();
                    for (Product product : arrayProduct){
                        Log.d("fsggegerger",product.getName());
                        if (product.getName().toLowerCase().contains(s.toString().toLowerCase())){

                            arrayProductSearch.add(product);
                        }
                    }
                    searchProductAdapter.notifyDataSetChanged();
                }else {
                    arrayProductSearch.clear();
                    searchProductAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    // Lấy danh sách đơn hàng về
    private void getArrayProduct() {
        FirebaseDatabase.getInstance().getReference()
                .child("Product")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()){
                            arrayProduct.add(dataSnapshot.getValue(Product.class));
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
        recOrder.setLayoutManager(new LinearLayoutManager(this));
        searchProductAdapter = new SearchProductAdapter(arrayProductSearch);
        recOrder.setAdapter(searchProductAdapter);
    }

    // Ánh xạ
    private void initView() {
        edtSearch   = findViewById(R.id.edtSearch);
        recOrder    = findViewById(R.id.recOrder);
    }

    public void onClickCancel(View view) {
        finish();
    }
}
