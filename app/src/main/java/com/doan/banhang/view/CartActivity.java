package com.doan.banhang.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.CartAdapter;
import com.doan.banhang.callback.OnUpdatePriceListener;
import com.doan.banhang.model.Order;
import com.doan.banhang.utils.AccountUtils;
import com.doan.banhang.utils.CartUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recCart;
    private TextView    txtTotalPrice;
    private CartAdapter cartAdapter;
    private ArrayList<Order> arrayOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        setupToolbar();
        createCartList();
        addEvents();
    }

    private void addEvents() {
        // Lắng nghe sự kiện người dùng thay đổi đơn hàng bao gồm thêm, bớt số lượng sản phẩm mua hoặc tick chọn và không chọn
        cartAdapter.setOnUpdatePriceListener(new OnUpdatePriceListener() {
            @Override
            public void onUpdate() {
                long price = 0;

                // Dùng vòng for để kiểm tra trường hợp đối tượng đc tick ở checkbox thì cộng tiền
                for (Order order : arrayOrder){
                    if (order.isChecked()){
                        price = price + (Integer.valueOf(order.getProduct().getPrice()) * order.getAmount());
                    }
                }

                // Hiển thị lên textview
                txtTotalPrice.setText("Tổng tiền: " + new DecimalFormat("###,###").format(price).replace(","," ") + " VND");
            }
        });
    }

    // Khởi tạo toolbar và tạo 1 nút back trên toolbar
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
    }

    private void createCartList() {
        // Lấy dữ liệu giỏ hàng từ sqlite
        arrayOrder = CartUtils.getInstance().getCart();

        // Khởi tạo recyclerview và adapter
        recCart.setHasFixedSize(true);
        recCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(arrayOrder);
        recCart.setAdapter(cartAdapter);
    }

    // Ánh xạ
    private void initView() {
        recCart         = findViewById(R.id.recCart);
        txtTotalPrice   = findViewById(R.id.txtTotalPrice);
    }

    // Bắt sự kiện người dùng click vào button mua
    public void onClickByOrder(View view) {
        for (int i=0; i < arrayOrder.size(); i++){
            if (arrayOrder.get(i).isChecked()){
                FirebaseDatabase.getInstance().getReference()
                        .child("Buying")
                        .child(AccountUtils.getInstance().getAccount().getUsername())
                        .child(System.currentTimeMillis() + i + "")
                        .setValue(arrayOrder.get(i));
            }
        }
    }

    // Lắng nghe sự kiện người dùng click vào nút back trên toolbar và trở về màn hình trước đó
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
