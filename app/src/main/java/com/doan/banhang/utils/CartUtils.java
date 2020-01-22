package com.doan.banhang.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.doan.banhang.R;
import com.doan.banhang.base.BaseApplication;
import com.doan.banhang.model.Order;
import com.doan.banhang.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CartUtils extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_CART = "CREATE TABLE IF NOT EXISTS Cart(ID VARCHAR(100),PRODUCT VARCHAR(10000),AMOUNT INTEGER(100))";
    private static final String SELECT_TABLE_CART = "SELECT * FROM Cart";
    private static final String DELETE_PRODUCE_IN_CART = "DELETE FROM Cart WHERE ID = ";

    private static CartUtils cartUtils;

    public static CartUtils getInstance(){
        if (cartUtils == null){
            cartUtils = new CartUtils();
        }
        return cartUtils;
    }

    private CartUtils() {
        super(BaseApplication.getContext(), BaseApplication.getContext().getString(R.string.app_name) + ".sql", null, 1);
        queryData(CREATE_TABLE_CART);
    }

    private void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    private Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertProduct(Product product){
        Cursor cursor = getData(SELECT_TABLE_CART);
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(product.getIdProduct())){
                return true;
            }
        }

        String insertQuery = "INSERT INTO Cart VALUES('" + product.getIdProduct() + "','" + new Gson().toJson(product) + "',1)";
        queryData(insertQuery);

        return false;
    }

    public ArrayList<Order> getCart(){
        ArrayList<Order> arrayOrder = new ArrayList<>();

        Cursor cursor = getData(SELECT_TABLE_CART);
        while (cursor.moveToNext()){
            arrayOrder.add(new Order(new Gson().fromJson(cursor.getString(1),Product.class),cursor.getInt(2)));
        }
        return arrayOrder;
    }

    public void deleteProduct(String idProduct){
        queryData(DELETE_PRODUCE_IN_CART + idProduct);
    }
}
