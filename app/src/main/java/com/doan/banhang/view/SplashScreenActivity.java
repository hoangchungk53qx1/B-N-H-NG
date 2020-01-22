package com.doan.banhang.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.doan.banhang.R;
import com.doan.banhang.utils.MediaUtils;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        MediaUtils.getInstance().play(R.raw.ic_raw_shopee);


        // Hẹn 2 giây sau sẽ chuyển sang màn hình đăng nhập
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        },1000);



    }
}
