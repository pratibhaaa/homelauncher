package com.example.home_launcher;

import android.graphics.drawable.Drawable;

public class Item {

   private  String name,pack,v_code,v_name;
  private   Drawable icon;

    public Item(String name, String pack, String v_code, String v_name, Drawable icon) {
        this.name = name;
        this.pack = pack;
        this.v_code = v_code;
        this.v_name = v_name;
        this.icon = icon;
    }

    public String getV_code() {
        return v_code;
    }

    public void setV_code(String v_code) {
        this.v_code = v_code;
    }

    public String getV_name() {
        return v_name;
    }

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
