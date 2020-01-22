package com.doan.banhang.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.doan.banhang.base.BaseApplication;
import com.doan.banhang.base.Constants;
import com.doan.banhang.model.Account;
import com.google.gson.Gson;

public class AccountUtils {

    private static AccountUtils accountUtils;
    private static SharedPreferences sharedPreferences;

    public static AccountUtils getInstance(){
        if (accountUtils == null){
            accountUtils = new AccountUtils();
            sharedPreferences = BaseApplication.getContext().getSharedPreferences("Account", Context.MODE_PRIVATE);
        }
        return accountUtils;
    }

    // Chuyển đối tượng Account thành chuỗi json và lưu vào SharedPreferences
    public void setAccount(Account account){
        sharedPreferences.edit().putString(Constants.ACCOUNT,account.toString()).apply();
    }

    // Lấy chuỗi json Account từ SharedPreferences và chuyển thành đối tượng Account
    public Account getAccount(){
        return new Gson().fromJson(sharedPreferences.getString(Constants.ACCOUNT,null),Account.class);
    }
}
