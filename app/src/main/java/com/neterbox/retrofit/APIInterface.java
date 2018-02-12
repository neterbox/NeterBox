package com.neterbox.retrofit;

import com.neterbox.CirclePost;
import com.neterbox.FriendRequestList;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddP;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriend;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriendRequest;
import com.neterbox.jsonpojo.FriendRequestList.FriendRequestListpojo;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriend;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequest;
import com.neterbox.jsonpojo.circle.Circlepage;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListPojo;
import com.neterbox.jsonpojo.country.Country;
import com.neterbox.jsonpojo.editprofile.Editpage;
import com.neterbox.jsonpojo.near_by_friend.Nearbyfriend;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.jsonpojo.sendfriendrequest.SendRequest;
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
                              @Field("password") String password);

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
                                @Field("title") String title);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.REGISTER)
    Call<RegistrationPojo> registerPojoCall(@Field("name") String name,
                                            @Field("username") String username,
                                            @Field("password") String password,
                                            @Field("birth_date") String birth_date,
                                            @Field("email") String email,
                                            @Field("type") int type);

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
                                       @Field("latitude") Double latitude,
                                       @Field("longitude") Double longitude);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.SENDREQUEST)
    Call<SendRequest> sendrequestpojo(@Field("sender_id") String sender_id,
                                      @Field("receiver_id") String receiver_id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLELIST)
    Call<Circlepage> Circlelistpojo(@Field("index") String index);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.DECLINEFRIENDREQUEST)
    Call<DeclineFriend> DeclineFriendListtpojo(@Field("id") String id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.FRIENDREQUESTLIST)
    Call<FriendRequestListpojo> friendRequestListpojocall(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLEPOSTLIST)
    Call<CirclePostListPojo> circlepostlistpojocall(@Field("index") String index,
                                                    @Field("circle_id") String circle_id,
                                                    @Field("countries_id") String countries_id,
                                                    @Field("state_id") String state_id);

    @Multipart
    @POST(ServerUrl.CIRCLEPOSTLISTADD)
    Call<CirclePostAddP> circlepostaddpojocall(@Part("user_id") String user_id,
                                               @Part("circle_id") String circle_id,
                                               @Part("circle_id") String countries_id,
                                               @Part("state_id") String state_id,
                                               @Part("comments") String comments,
                                               @Part MultipartBody.Part post_files);

    @Headers({"Content-Type: 3application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLEPOSTLISTDELET)
    Call<CirclePostDeleteP> circlepostdeletepojocall(@Field("id") String id);

    @Headers({"Content-Type: 3application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.ACCEPTFRIENDREQUEST)
    Call<AcceptFriendRequest> acceptFriendRequestCall(String id);

    @Headers({"Content-Type: 3application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.ACCEPTFRIENDREQUEST)
    Call<DeclineFriendRequest> declineFriendRequestCall(String id);

}