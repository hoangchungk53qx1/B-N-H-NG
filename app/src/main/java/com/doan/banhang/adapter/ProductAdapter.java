package com.doan.banhang.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doan.banhang.R;
import com.doan.banhang.base.Constants;
import com.doan.banhang.model.Product;
import com.doan.banhang.utils.ScreenUtils;
import com.doan.banhang.view.DetailProductActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private ArrayList<Product> arrayProduct;

    public ProductAdapter(ArrayList<Product> arrayProduct) {
        this.arrayProduct = arrayProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.txtName.setText(arrayProduct.get(i).getName());
        viewHolder.txtPrice.setText("đ" + new DecimalFormat("###,###").format(Integer.valueOf(arrayProduct.get(i).getPrice())).replace(","," "));

        Glide.with(viewHolder.imgPicture.getContext())
                .load(arrayProduct.get(i).getArrayPictureIntroduce().get(0))
                .into(viewHolder.imgPicture);
    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView        txtName,txtPrice;
        ImageView       imgPicture;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName         = itemView.findViewById(R.id.txtName);
            imgPicture      = itemView.findViewById(R.id.imgPicture);
            txtPrice        = itemView.findViewById(R.id.txtPrice);

            imgPicture.setLayoutParams(new LinearLayout.LayoutParams((ScreenUtils.getInstance().getWidth()/ 2) - 5,ScreenUtils.getInstance().getWidth()/ 2));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailProductActivity.class);
                    intent.putExtra(Constants.PRODUCT, arrayProduct.get(getAdapterPosition()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}