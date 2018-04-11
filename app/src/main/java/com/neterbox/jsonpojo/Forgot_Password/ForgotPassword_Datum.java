package com.neterbox.jsonpojo.Forgot_Password;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPassword_Datum{


    @SerializedName("User")
    @Expose
    private Forgotpassword_user user;

    public Forgotpassword_user getUser() {
        return user;
    }

    public void setUser(Forgotpassword_user user) {
        this.user = user;
    }


}
