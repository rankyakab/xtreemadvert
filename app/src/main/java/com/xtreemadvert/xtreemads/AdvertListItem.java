package com.xtreemadvert.xtreemads;

import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdvertListItem {

    private String cat,item_name,details,price,other_info,file_name,owner,phonenumber,businessname,address;
    private int id,status;

    public AdvertListItem(String cat, String item_name, String details, String price, String other_info, String file_name, String owner, int id, int status, String businessname, String address, String phonenumber) {
        this.cat = cat;
        this.item_name = item_name;
        this.details = details;
        this.price = price;
        this.other_info = other_info;
        this.file_name = file_name;
        this.owner = owner;
        this.phonenumber = phonenumber;
        this.businessname =businessname;
        this.address =address;
        this.id = id;
        this.status = status;
    }

    public String getCat() {
        return cat;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getDetails() {
        return details;
    }

    public String getPrice() {
        return price;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getBusinessname() {
        return businessname;
    }

    public String getAddress() {
        return address;
    }

    public String getOther_info() {
        return other_info;
    }

    public String getFile_name() {
        return "http://xtreemadvert.com/products/"+file_name;
    }

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }
}
