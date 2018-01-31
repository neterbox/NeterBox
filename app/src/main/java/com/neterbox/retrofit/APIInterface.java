package com.neterbox.retrofit;

import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriend;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriend;
import com.neterbox.jsonpojo.circle.Circlepage;
import com.neterbox.jsonpojo.country.Country;
import com.neterbox.jsonpojo.editprofile.Editpage;
import com.neterbox.jsonpojo.near_by_friend.Nearbyfriend;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.jsonpojo.state.State;
import com.neterbox.jsonpojo.uploadpic.Uploadpic;
import com.neterbox.utils.ServerUrl;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
                                            @Field("birth_date") String birth_date,
                                            @Field("email") String email,
                                            @Field("type") int type );
    @Multipart
    @POST(ServerUrl.UPLOADPIC)
    Call<Uploadpic> uploadPic(@Part("id") RequestBody login_id,
                                            @Part MultipartBody.Part user_profile);

    @GET(ServerUrl.COUNTRY)
    Call<Country> countrypojo();

    @GET(ServerUrl.STATE + "/{country_id}")
    Call<State> statepojo(@Path("country_id") String country_id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.NERBYFRIEND)
    Call<Nearbyfriend> Nerbyfriendpojo(@Field("id") String id,
                                       @Field("latitude") String latitude,
                                       @Field("longitude") String longitude );


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLELIST)
    Call<Circlepage> Circlelistpojo(@Field("index") String index);



    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.ACCEPTFRIENDREQUEST)
    Call<AcceptFriend> AcceptFriendListtpojo(@Field("id") String id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.DECLINEFRIENDREQUEST)
    Call<DeclineFriend> DeclineFriendListtpojo(@Field("id") String id);
}


