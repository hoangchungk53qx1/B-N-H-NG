package com.doan.banhang.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.doan.banhang.fragment.AccountFragment;
import com.doan.banhang.fragment.HouseFragment;
import com.doan.banhang.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);

        initView();
        loadFragment(new HouseFragment());
    }

    private void initView() {
        // Ánh xạ và khởi tạo BottomNavigationView
        // Link tham khảo: https://viblo.asia/p/lam-viec-voi-bottom-navigation-trong-android-gGJ59j6pKX2
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    // Lắng nghe người dùng click vào menu trên BottomNavigationView và hiển thị ra view tương ứng
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    loadFragment(new HouseFragment());
                    return true;
                case R.id.menu_account:
                    loadFragment(new AccountFragment());
                    return true;
            }
            return false;
        }
    };

    // Hiển thị fragment trên view
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
