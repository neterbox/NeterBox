package com.neterbox.retrofit;

import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.editprofile.Editpage;
import com.neterbox.Registration;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.utils.ServerUrl;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by DeLL on 12-01-2018.
 */

public interface APIInterface {

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.LOGIN)
    Call<Login> loginpojocall(@Field("email") String username,
                              @Field("password") String password) ;

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.EDITPROFILE)
    Call<Editpage> editpojocall(@Field("id") String id,
                                 @Field("name") String name,
                                 @Field("username") String username,
                                 @Field("password") String password,
                                 @Field("birth_date") String birth_date,
                                 @Field("phone_number") String phone_number,
                                 @Field("email") String email,
                                 @Field("gender") String gender,
                                 @Field("address") String address,
                                 @Field("company") String company,
                                 @Field("title") String title) ;


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.REGISTER)
    Call<RegistrationPojo> registerPojoCall(@Field("name") String name,
                                            @Field("username") String username,
                                            @Field("password") String password,
                                            @Field("birth_date ") String birth_date,
                                            @Field("email") String email,
                                            @Field("type") int type );


}


