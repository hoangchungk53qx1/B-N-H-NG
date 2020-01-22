package com.doan.banhang.model;

public class Order {
    private Product product;
    private int     amount;

    private boolean isChecked;

    public Order(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
