package com.facebook.ads;

public class RewardData {
    private String f197a;
    private String f198b;

    public RewardData(String str, String str2) {
        this.f197a = str;
        this.f198b = str2;
    }

    public String getCurrency() {
        return this.f198b;
    }

    public String getUserID() {
        return this.f197a;
    }
}
