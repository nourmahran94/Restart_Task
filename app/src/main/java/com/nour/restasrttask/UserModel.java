package com.nour.restasrttask;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("email")
    private String email ;

    @SerializedName("password")
    private String password ;

    public String getEmail() {
        return email;
    }

    public String getPasswor() {
        return password;
    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
