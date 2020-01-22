package com.doan.banhang.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.base.Constants;
import com.doan.banhang.model.Product;
import com.doan.banhang.view.DetailProductActivity;

import java.util.ArrayList;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder>{

    private ArrayList<Product> arrayProduct;

    public SearchProductAdapter(ArrayList<Product> arrayProduct) {
        this.arrayProduct = arrayProduct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product_search,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txtName.setText(arrayProduct.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return arrayProduct.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName         = itemView.findViewById(R.id.txtName);

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