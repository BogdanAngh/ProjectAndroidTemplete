package com.example.duy.calculator.converter;

import java.io.Serializable;

public class ItemUnitConverter implements Serializable {
    public static final long serialVersionUID = 12312312434L;
    private String res;
    private String title;
    private String unit;

    public ItemUnitConverter(String title, String res, String unit) {
        this.title = title;
        this.res = res;
        this.unit = unit;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRes() {
        return this.res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
