package com.doan.banhang.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.doan.banhang.R;
import com.doan.banhang.adapter.PictureIntroduceAdapter;
import com.doan.banhang.base.Constants;
import com.doan.banhang.model.Product;
import com.doan.banhang.utils.CartUtils;

import java.text.DecimalFormat;

public class DetailProductActivity extends AppCompatActivity {

    private Product     product;
    private ViewPager   viewPagerIntroduce;
    private TextView    txtNameOrder,txtPrice,txtIntroduce,txtAmount,txtTrademark,txtDesigns,txtMaterial,txtOrigin,txtScreen,txtCamera,txtCPU,txtGPU,txtRam,txtPin,txtOperatingSystem,txtSize,txtColor,txtWeight,txtStatus,txtPackingSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        initView();
        getIntentData();
        loadUI();
        createPager();
    }

    // Khởi tạo viewpager và adapter để hiện thị danh sách hình ảnh
    private void createPager() {
        PictureIntroduceAdapter pictureIntroduceAdapter = new PictureIntroduceAdapter(product.getArrayPictureIntroduce());
        viewPagerIntroduce.setAdapter(pictureIntroduceAdapter);
    }

    // Lấy dữ liệu từ intent
    private void getIntentData() {
        product = getIntent().getParcelableExtra(Constants.PRODUCT);
    }

    // Hiện thị dữ liệu từ đối tượng được gửi kèm ở intent lên view
    private void loadUI() {
        txtNameOrder.setText(product.getName());
        txtPrice.setText("đ" + new DecimalFormat("###,###").format(Integer.valueOf(product.getPrice())).replace(","," "));
        txtIntroduce.setText(product.getIntroduce());
        txtAmount.setText(product.getAmount());
        txtTrademark.setText(product.getTrademark());
        txtDesigns.setText(product.getDesigns());
        txtMaterial.setText(product.getMaterial());
        txtOrigin.setText(product.getOrigin());
        txtScreen.setText(product.getScreen());
        txtCamera.setText(product.getCamera());
        txtCPU.setText(product.getCpu());
        txtGPU.setText(product.getGpu());
        txtRam.setText(product.getRam());
        txtPin.setText(product.getPin());
        txtOperatingSystem.setText(product.getOperatingSystem());
        txtSize.setText(product.getSize());
        txtColor.setText(product.getColor());
        txtWeight.setText(product.getWeight());
        txtStatus.setText(product.getStatus());
        txtPackingSize.setText(product.getPackingSize());
    }

    // Ánh xạ view
    private void initView() {
        txtNameOrder            = findViewById(R.id.txtNameOrder);
        txtPrice                = findViewById(R.id.txtPrice);
        txtIntroduce            = findViewById(R.id.txtIntroduce);
        txtAmount               = findViewById(R.id.txtAmount);
        txtTrademark            = findViewById(R.id.txtTrademark);
        txtDesigns              = findViewById(R.id.txtDesigns);
        txtMaterial             = findViewById(R.id.txtMaterial);
        txtOrigin               = findViewById(R.id.txtOrigin);
        txtScreen               = findViewById(R.id.txtScreen);
        txtCamera               = findViewById(R.id.txtCamera);
        txtCPU                  = findViewById(R.id.txtCPU);
        txtGPU                  = findViewById(R.id.txtGPU);
        txtRam                  = findViewById(R.id.txtRam);
        txtPin                  = findViewById(R.id.txtPin);
        txtOperatingSystem      = findViewById(R.id.txtOperatingSystem);
        txtSize                 = findViewById(R.id.txtSize);
        txtColor                = findViewById(R.id.txtColor);
        txtWeight               = findViewById(R.id.txtWeight);
        txtStatus               = findViewById(R.id.txtStatus);
        txtPackingSize          = findViewById(R.id.txtPackingSize);
        viewPagerIntroduce      = findViewById(R.id.viewPagerIntroduce);
    }

    // Lắng nghe sự kiện người dùng nhấn nút back
    public void onClickCancel(View view) {
        finish();
    }

    // Lắng nghe sự kiện người dùng nhấn nút thêm vào giỏ hàng
    public void onClickAddCart(View view) {
        if (CartUtils.getInstance().insertProduct(product)){
            startActivity(new Intent(getApplicationContext(),CartActivity.class));
        }else {
            Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }
}
