package com.doan.banhang.view;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.banhang.R;
import com.doan.banhang.adapter.PicturePickerAdapter;
import com.doan.banhang.base.Constants;
import com.doan.banhang.callback.NumberPickedListener;
import com.doan.banhang.model.PicturePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class PicturePickerActivity extends AppCompatActivity {

    private RecyclerView recPictureList;
    private PicturePickerAdapter pickImageAdapter;
    private ArrayList<PicturePicker> arrayPicturePicker = new ArrayList<>();
    private ArrayList<String>        arrayPicture = new ArrayList<>();

    private ImageView imgDoneAll;

    private TextView txtPictureCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_picker);

        initView();
        setupToolbar();
        createPictureList();
        getFilePaths();
        initEvent();
    }

    private void createPictureList() {
        recPictureList.setHasFixedSize(true);
        recPictureList.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        pickImageAdapter = new PicturePickerAdapter(arrayPicturePicker);
        recPictureList.setAdapter(pickImageAdapter);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void initEvent() {
        pickImageAdapter.setNumberPickedListener(new NumberPickedListener() {
            @Override
            public void onSelect() {
                arrayPicture.clear();
                for (PicturePicker picturePicker : arrayPicturePicker){
                    if (picturePicker.isChecked()){
                        arrayPicture.add(picturePicker.getPathPicture());
                    }
                }
                txtPictureCount.setText("Đã chọn: " + arrayPicture.size());

                if (arrayPicture.size() > 0){
                    imgDoneAll.setVisibility(View.VISIBLE);
                }else {
                    imgDoneAll.setVisibility(View.GONE);
                }
            }
        });
        imgDoneAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(Constants.PICTURE,arrayPicture);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void initView() {
        imgDoneAll          = findViewById(R.id.imgDoneAll);
        recPictureList      = findViewById(R.id.recPictureList);
        txtPictureCount     = findViewById(R.id.txtPictureCount);
    }

    private void getFilePaths() {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<>();

        String[] directories = null;
        if (u != null){
            c = managedQuery(u, projection, null, null, null);
        }

        if ((c != null) && (c.moveToFirst())){
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try{
                    dirList.add(tempDir);
                }
                catch(Exception ignored) {
                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);
        }
        for(int i=0;i<dirList.size();i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if(imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {
                    if(imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();
                    }
                    if ( imagePath.getName().contains(".jpg")|| imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg")|| imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")) {
                        String path= imagePath.getAbsolutePath();
                        arrayPicturePicker.add(new PicturePicker(path));
                        pickImageAdapter.notifyItemInserted(arrayPicturePicker.size() - 1);
                        Log.d("dgđfgdfgdfg",arrayPicturePicker.size() + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)finish();
        return super.onOptionsItemSelected(item);
    }
}
