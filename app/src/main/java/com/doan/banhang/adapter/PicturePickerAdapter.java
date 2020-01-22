package com.doan.banhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doan.banhang.R;
import com.doan.banhang.callback.NumberPickedListener;
import com.doan.banhang.model.PicturePicker;

import java.util.ArrayList;

public class PicturePickerAdapter extends RecyclerView.Adapter<PicturePickerAdapter.ViewHolder>  {

    private ArrayList<PicturePicker>    arrayImages;
    private NumberPickedListener        numberPickedListener;

    public PicturePickerAdapter(ArrayList<PicturePicker> arrayImages) {
        this.arrayImages = arrayImages;
    }

    public void setNumberPickedListener(NumberPickedListener numberPickedListener) {
        this.numberPickedListener = numberPickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_picture_picker,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        Glide.with(holder.imgPicture.getContext())
                .load(arrayImages.get(i).getPathPicture())
                .into(holder.imgPicture);

        holder.cbSelect.setChecked(arrayImages.get(holder.getAdapterPosition()).isChecked());
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                arrayImages.get(holder.getAdapterPosition()).setChecked(isChecked);
                numberPickedListener.onSelect();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayImages.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView   imgPicture;
        CheckBox    cbSelect;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPicture  = itemView.findViewById(R.id.imgPicture);
            cbSelect    = itemView.findViewById(R.id.cbSelect);
        }
    }
}