package com.doan.banhang.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.doan.banhang.R;
import com.doan.banhang.model.Account;
import com.doan.banhang.utils.AccountUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername,edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    public void onClickLogin(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        // Kiểm tra dữ liệu nhập vào có null hay không
        if (TextUtils.isEmpty(username)){
            Toast.makeText(getApplicationContext(), "Bạn chưa điền tên đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Bạn chưa điền mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        // Đọc dữ liệu từ database về và kiểm tra xem nó có tồn tại hay không
        FirebaseDatabase.getInstance().getReference()
                .child("Account")
                .child(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            // Tồn tại thì sẽ lấy dữ liệu về
                            Account account = dataSnapshot.getValue(Account.class);

                            // Kiểm tra xem password nhập vào có trùng với password của tài khoản trên database hay không
                            if (Objects.requireNonNull(account).getPassword().equals(password)){

                                // Nếu tồn tại thì sẽ lưu dữ liệu lại và chuyển đến mình hình chính
                                AccountUtils.getInstance().setAccount(account);

                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),HouseActivity.class));
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            // Không tồn tại thì sẽ thêm tài khoản đó vào database
                            saveAccount(username,password);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveAccount(String username, String password) {
        // Khởi tạo đối tượng
        final Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        account.setEmail("abc@gmail.com");
        account.setName("taikhoan" + new Random().nextInt(100000));

        // Thêm đối tượng vào database
        FirebaseDatabase.getInstance().getReference()
                .child("Account")
                .child(username)
                .setValue(account, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError == null){
                            // Thêm thành công sẽ lưu lại đối tượng và chuyển đến màn hình chính
                            Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();

                            AccountUtils.getInstance().setAccount(account);

                            startActivity(new Intent(getApplicationContext(),HouseActivity.class));
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
