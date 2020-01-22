package com.doan.banhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doan.banhang.R;
import com.doan.banhang.callback.OnUpdatePriceListener;
import com.doan.banhang.model.Order;
import com.doan.banhang.utils.CartUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<Order> arrayOrder;
    private OnUpdatePriceListener onUpdatePriceListener;

    public CartAdapter(ArrayList<Order> arrayOrder) {
        this.arrayOrder = arrayOrder;
    }

    public void setOnUpdatePriceListener(OnUpdatePriceListener onUpdatePriceListener) {
        this.onUpdatePriceListener = onUpdatePriceListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtName.setText(arrayOrder.get(position).getProduct().getName());
        holder.txtCount.setText(arrayOrder.get(holder.getAdapterPosition()).getAmount() + "");
        holder.txtPrice.setText("đ" + new DecimalFormat("###,###").format(Integer.valueOf(arrayOrder.get(position).getProduct().getPrice())).replace(","," "));

        Glide.with(holder.imgPicture.getContext())
                .load(arrayOrder.get(position).getProduct().getArrayPictureIntroduce().get(0))
                .into(holder.imgPicture);
    }

    @Override
    public int getItemCount() {
        return arrayOrder.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox cbSelect;

        ImageView imgPicture,imgRemove,imgAdd,imgDelete;
        TextView txtName,txtCount,txtPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPicture = itemView.findViewById(R.id.imgPicture);
            txtName = itemView.findViewById(R.id.txtName);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            txtCount = itemView.findViewById(R.id.txtCount);
            imgAdd = itemView.findViewById(R.id.imgAdd);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cbSelect = itemView.findViewById(R.id.cbSelect);

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    arrayOrder.get(getAdapterPosition()).setChecked(isChecked);
                    onUpdatePriceListener.onUpdate();
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartUtils.getInstance().deleteProduct(arrayOrder.get(getAdapterPosition()).getProduct().getIdProduct());
                    arrayOrder.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    onUpdatePriceListener.onUpdate();
                }
            });

            imgRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayOrder.get(getAdapterPosition()).getAmount() > 0) {
                        arrayOrder.get(getAdapterPosition()).setAmount(arrayOrder.get(getAdapterPosition()).getAmount() - 1);
                        txtCount.setText(arrayOrder.get(getAdapterPosition()).getAmount() + "");
                        onUpdatePriceListener.onUpdate();
                    }
                }
            });

            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (arrayOrder.get(getAdapterPosition()).getAmount() < 99) {
                        arrayOrder.get(getAdapterPosition()).setAmount(arrayOrder.get(getAdapterPosition()).getAmount() + 1);
                        txtCount.setText(arrayOrder.get(getAdapterPosition()).getAmount() + "");
                        onUpdatePriceListener.onUpdate();
                    }
                }
            });
        }
    }
}