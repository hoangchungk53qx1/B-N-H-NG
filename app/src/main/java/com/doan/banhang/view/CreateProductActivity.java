package com.doan.banhang.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.AddPictureIntroduceAdapter;
import com.doan.banhang.base.Constants;
import com.doan.banhang.callback.OnPickPictureListener;
import com.doan.banhang.model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class CreateProductActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 101;
    private AddPictureIntroduceAdapter pictureIntroduceAdapter;
    private RecyclerView recPictureIntroduce;
    private ArrayList<String>   arrayPicture = new ArrayList<>();

    private EditText edtName,edtIntroduce,edtTrademark,edtDesigns,edtMaterial,edtOrigin,edtScreen,edtCPU,edtGPU,edtRam,edtPin,edtOperatingSystem,edtSize,edtPrice,edtAmount,edtColor,edtWeight,edtStatus,edtPackingSize;
    private TextView txtNameCount,txtIntroduceCount;

    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        arrayPicture.add("https://cdn.fptshop.com.vn/Uploads/Originals/2019/9/11/637037687763926758_11-pro-max-xanh.png");
        arrayPicture.add("https://cdn.tgdd.vn/Products/Images/42/210653/iphone-11-pro-max-256gb-green-400x460.png");
        arrayPicture.add("https://shopdunk.com/wp-content/uploads/2019/09/iP11Pro-3.jpg");
        arrayPicture.add("https://didongviet.vn/pub/media/wysiwyg/Dien-Thoai/Apple-iPhone/ip-11-pro-max/iphone-11-pro-11-pro-max-didongviet-5.jpg");
        arrayPicture.add("https://image.forbesvietnam.com.vn/w645/uploaded/oizwrg/oqivo.tm/2019_09_11/apple_iphone_11_rosette_family_lineup_091019_big_jpg_large_2x_ljws.jpg");
        arrayPicture.add("https://stcv4.hnammobile.com//uploads/products/colors/6/apple-iphone-11-pro-max-2-sim-64gb-01568625931.jpg");

        initView();
        createImageIntroduce();
        addEvents();
    }

    private void addEvents() {
        // Lắng nghe sự kiện người dùng click vào button thêm ảnh
        pictureIntroduceAdapter.setOnPickPictureListener(new OnPickPictureListener() {
            @Override
            public void onPicker() {
                // Kiểm tra xem người dùng đã cấp quyền đọc ghi bộ nhớ hay chưa
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                        return;
                    }
                }
                // Đi đến màn hình chọn hình ảnh
                startActivityForResult(new Intent(getApplicationContext(), PicturePickerActivity.class), 113);
            }
        });
        // Lắng nghe sự kiện người dùng thêm hay bớt text trên edittext tên sản phẩm
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtNameCount.setText(s.length()+"/120");
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        // Lắng nghe sự kiện người dùng thêm hay bớt text trên edittext lời giới thiệu
        edtIntroduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 100){
                    txtIntroduceCount.setText("Cần thêm " + (100 - s.length()) + " ký tự nữa");
                }else {
                    txtIntroduceCount.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createImageIntroduce() {
        // Khởi tạo recyclerview và adapter
        recPictureIntroduce.setHasFixedSize(true);
        recPictureIntroduce.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        pictureIntroduceAdapter = new AddPictureIntroduceAdapter(arrayPicture);
        recPictureIntroduce.setAdapter(pictureIntroduceAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 113 && resultCode == RESULT_OK && data != null){
            // Nhận về danh sách hình ảnh mà người dùng đã chọn

            ArrayList<String> arrayPictureResult = data.getStringArrayListExtra(Constants.PICTURE);

            // Nếu danh sách nhận về rỗng thì dừng các câu lệnh ở phía dưới nó
            if (arrayPictureResult == null){
                return;
            }

            // Hiển thị danh sách hình ảnh vào recyclerview
            arrayPicture.addAll(arrayPictureResult);
            pictureIntroduceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Nhận về kết quả người dùng có cho phép quyền đọc ghi bộ nhớ hay không
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(getApplicationContext(), PicturePickerActivity.class), 113);
            }
        }
    }

    // Ánh xạ các view
    private void initView() {
        recPictureIntroduce = findViewById(R.id.recPictureIntroduce);
        txtNameCount        = findViewById(R.id.txtNameCount);
        edtName             = findViewById(R.id.edtName);
        edtIntroduce        = findViewById(R.id.edtIntroduce);
        txtIntroduceCount   = findViewById(R.id.txtIntroduceCount);
        edtTrademark        = findViewById(R.id.edtTrademark);
        edtDesigns          = findViewById(R.id.edtDesigns);
        edtMaterial         = findViewById(R.id.edtMaterial);
        edtOrigin           = findViewById(R.id.edtOrigin);
        edtScreen           = findViewById(R.id.edtScreen);
        edtCPU              = findViewById(R.id.edtCPU);
        edtGPU              = findViewById(R.id.edtGPU);
        edtRam              = findViewById(R.id.edtRam);
        edtPin              = findViewById(R.id.edtPin);
        edtOperatingSystem  = findViewById(R.id.edtOperatingSystem);
        edtSize             = findViewById(R.id.edtSize);
        edtPrice            = findViewById(R.id.edtPrice);
        edtAmount           = findViewById(R.id.edtAmount);
        edtColor            = findViewById(R.id.edtColor);
        edtWeight           = findViewById(R.id.edtWeight);
        edtStatus           = findViewById(R.id.edtStatus);
        edtPackingSize      = findViewById(R.id.edtPackingSize);

        // Khởi tạo 1 progressdialog thông báo người dùng chờ đợi
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Đang tải lên sản phẩm...");
        progressBar.setCancelable(false);
    }

    public void onClickAddOrder(View view) {
        String name             = edtName.getText().toString();
        String introduce        = edtIntroduce.getText().toString();
        String trademark        = edtTrademark.getText().toString();
        String designs          = edtDesigns.getText().toString();
        String material         = edtMaterial.getText().toString();
        String origin           = edtOrigin.getText().toString();
        String screen           = edtScreen.getText().toString();
        String cpu              = edtCPU.getText().toString();
        String gpu              = edtGPU.getText().toString();
        String ram              = edtRam.getText().toString();
        String pin              = edtPin.getText().toString();
        String operatingSystem  = edtOperatingSystem.getText().toString();
        String size             = edtSize.getText().toString();
        String price            = edtPrice.getText().toString();
        String amount           = edtAmount.getText().toString();
        String color            = edtColor.getText().toString();
        String weight           = edtWeight.getText().toString();
        String status           = edtStatus.getText().toString();
        String packingSize      = edtPackingSize.getText().toString();

        // Kiểm tra xem các trường nhập vào có null hay không, nếu null sẽ Toast thông báo
        if (arrayPicture.size() == 0){
            Toast.makeText(this, "Vui lòng tải lên ít nhất 1 ảnh về sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Tên sản phẩm không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(introduce)){
            Toast.makeText(this, "Lời giới thiệu sản phẩm không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (introduce.length() < 101){
            Toast.makeText(this, "Lời giới thiệu sản phẩm phải có hơn 100 ký tự !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(trademark)){
            Toast.makeText(this, "Thương hiệu không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(designs)){
            Toast.makeText(this, "Kiểu dáng không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(material)){
            Toast.makeText(this, "Chất liệu không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(origin)){
            Toast.makeText(this, "Xuất xứ không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(screen)){
            Toast.makeText(this, "Màn hình không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(cpu)){
            Toast.makeText(this, "CPU không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(gpu)){
            Toast.makeText(this, "GPU không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(ram)){
            Toast.makeText(this, "Ram không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pin)){
            Toast.makeText(this, "Pin không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(operatingSystem)){
            Toast.makeText(this, "Hệ điều hành không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(size)){
            Toast.makeText(this, "Kích thước không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Gía tiền không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(amount)){
            Toast.makeText(this, "Số lượng không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(color)){
            Toast.makeText(this, "Màu sắc không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(weight)){
            Toast.makeText(this, "Cân nặng không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(status)){
            Toast.makeText(this, "Tình trạng không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(packingSize)){
            Toast.makeText(this, "Kích thước đóng gói không được để trống !", Toast.LENGTH_SHORT).show();
            return;
        }

        String idOrder = "" + System.currentTimeMillis();

        // Thêm dữ liệu vào đối tượng
        Product product = new Product();
        product.setIdProduct(idOrder);
        product.setName(name);
        product.setIntroduce(introduce);
        product.setTrademark(trademark);
        product.setDesigns(designs);
        product.setMaterial(material);
        product.setOrigin(origin);
        product.setScreen(screen);
        product.setCpu(cpu);
        product.setGpu(gpu);
        product.setRam(ram);
        product.setPin(pin);
        product.setOperatingSystem(operatingSystem);
        product.setSize(size);
        product.setPrice(price);
        product.setAmount(amount);
        product.setColor(color);
        product.setWeight(weight);
        product.setStatus(status);
        product.setPackingSize(packingSize);

//        uploadArrayPicture(product);

        //test
        arrayPicture.remove("Demo");
        product.setArrayPictureIntroduce(arrayPicture);
        insertOrder(product);
    }

    // Tải lên danh sách hình ảnh
    private void uploadArrayPicture(final Product product) {
        progressBar.show();
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        final ArrayList<String> arrayPictureResult = new ArrayList<>();

        arrayPicture.remove("Demo");

        for (int i=0; i < arrayPicture.size(); i++){
            try {
                InputStream inputStream = new FileInputStream(arrayPicture.get(i));

                UploadTask uploadTask = storageReference.child(System.currentTimeMillis() + i + ".png").putStream(inputStream);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            arrayPictureResult.add(downloadUri.toString());

                            if (arrayPictureResult.size() == arrayPicture.size()){
                                product.setArrayPictureIntroduce(arrayPictureResult);
                                insertOrder(product);
                            }
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                progressBar.cancel();
                e.printStackTrace();
            }
        }
    }

    // Thêm dữ liệu vào database
    private void insertOrder(Product product) {
        FirebaseDatabase.getInstance().getReference()
                .child("Product")
                .child(product.getIdProduct())
                .setValue(product,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError == null){
                                    progressBar.cancel();
                                    Toast.makeText(getApplicationContext(), "Tải lên sản phẩm thành công", Toast.LENGTH_SHORT).show();
//                                    finish();
                                }else {
                                    progressBar.cancel();
                                    Toast.makeText(getApplicationContext(), "Tải lên sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }
}
