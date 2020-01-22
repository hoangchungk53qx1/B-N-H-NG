package com.doan.banhang.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Product implements Parcelable {
    // ID
    private String idProduct;

    // Ten san pham
    private String name;

    // Loi gioi thieu san pham
    private String introduce;

    // Thuong Hieu
    private String trademark;

    // Kieu dang
    private String designs;

    // Chat lieu
    private String material;

    // Xuat xu
    private String origin;

    // Man hinh
    private String screen;

    // May anh
    private String camera;

    // Chip xu ly
    private String cpu;

    // Chi do hoa
    private String gpu;

    // Ram
    private String ram;

    // Pin
    private String pin;

    // He dieu hanh
    private String operatingSystem;

    // Kich thuoc
    private String size;

    // Gia tien
    private String price;

    // So luong
    private String amount;

    // Mau sac
    private String color;

    // Can nang
    private String weight;

    // Tinh trang
    private String status;

    // Kich thuoc dong goi
    private String packingSize;

    // Danh sach hinh anh gioi thieu san pham
    private ArrayList<String> arrayPictureIntroduce;

    public Product() {
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public String getDesigns() {
        return designs;
    }

    public void setDesigns(String designs) {
        this.designs = designs;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPackingSize() {
        return packingSize;
    }

    public void setPackingSize(String packingSize) {
        this.packingSize = packingSize;
    }

    public ArrayList<String> getArrayPictureIntroduce() {
        return arrayPictureIntroduce;
    }

    public void setArrayPictureIntroduce(ArrayList<String> arrayPictureIntroduce) {
        this.arrayPictureIntroduce = arrayPictureIntroduce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idProduct);
        dest.writeString(this.name);
        dest.writeString(this.introduce);
        dest.writeString(this.trademark);
        dest.writeString(this.designs);
        dest.writeString(this.material);
        dest.writeString(this.origin);
        dest.writeString(this.screen);
        dest.writeString(this.camera);
        dest.writeString(this.cpu);
        dest.writeString(this.gpu);
        dest.writeString(this.ram);
        dest.writeString(this.pin);
        dest.writeString(this.operatingSystem);
        dest.writeString(this.size);
        dest.writeString(this.price);
        dest.writeString(this.amount);
        dest.writeString(this.color);
        dest.writeString(this.weight);
        dest.writeString(this.status);
        dest.writeString(this.packingSize);
        dest.writeStringList(this.arrayPictureIntroduce);
    }

    private Product(Parcel in) {
        this.idProduct = in.readString();
        this.name = in.readString();
        this.introduce = in.readString();
        this.trademark = in.readString();
        this.designs = in.readString();
        this.material = in.readString();
        this.origin = in.readString();
        this.screen = in.readString();
        this.camera = in.readString();
        this.cpu = in.readString();
        this.gpu = in.readString();
        this.ram = in.readString();
        this.pin = in.readString();
        this.operatingSystem = in.readString();
        this.size = in.readString();
        this.price = in.readString();
        this.amount = in.readString();
        this.color = in.readString();
        this.weight = in.readString();
        this.status = in.readString();
        this.packingSize = in.readString();
        this.arrayPictureIntroduce = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
