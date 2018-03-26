package com.neterbox.retrofit;

import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddPojo;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriendRequest;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.comment.Comment;
import com.neterbox.jsonpojo.following.Following;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequest;
import com.neterbox.jsonpojo.cancel_friend_request.CancelFriendRequest;
import com.neterbox.jsonpojo.circle.Circlepage;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.circlepostlist.CirclePostListPojo;
import com.neterbox.jsonpojo.city.City;
import com.neterbox.jsonpojo.country.Country;
import com.neterbox.jsonpojo.editprofile.Editpage;
import com.neterbox.jsonpojo.friend_list.FriendListPojo;
import com.neterbox.jsonpojo.get_profile.GetProfile;
import com.neterbox.jsonpojo.like.Like;
import com.neterbox.jsonpojo.near_by_friend.Nearbyfriend;
import com.neterbox.jsonpojo.register.RegistrationPojo;
import com.neterbox.jsonpojo.sendfriendrequest.SendRequest;
import com.neterbox.jsonpojo.state.State;
import com.neterbox.jsonpojo.unlike.Unlike;
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
    Call<Login> loginpojocall(@Field("email") String email,
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

    @GET(ServerUrl.FRIENDLIST + "/{login_id}")
    Call<FriendListPojo> friendlistpojo(@Path("login_id") String login_id);

    @GET(ServerUrl.FRIENDREQUESTLIST + "/{loginuserid}")
    Call<FrndReqListModel> friendRequestListpojocall(@Path("loginuserid") String loginuserid);

    @GET(ServerUrl.STATE + "/{country_id}")
    Call<State> statepojo(@Path("country_id") String country_id);

    @GET(ServerUrl.CITY + "/{state_id}")
    Call<City> citypojo(@Path("state_id") String state_id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.NERBYFRIEND)
    Call<Nearbyfriend> Nerbyfriendpojo(@Field("id") String id,
                                       @Field("latitude") Double latitude,
                                       @Field("longitude") Double longitude );


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
    @POST(ServerUrl.ACCEPTFRIENDREQUEST)
    Call<AcceptFriendRequest> AcceptFriendListtpojo(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.ADDCHAT)
    Call<AddChat> addchatpojo(@Field("sender_id") String id,
                              @Field("receiver_id") String receiver_id,
                              @Field("sender_qb_id") String sender_qb_id,
                              @Field("receiver_qb_id") String receiver_qb_id,
                              @Field("dialog_id") String dialog_id,
                              @Field("dialog_type") String dialog_type,
                              @Field("dialog_status") String dialog_status,
                              @Field("group_name") String group_name);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.DECLINEFRIENDREQUEST)
    Call<DeclineFriendRequest> DeclineFriendListtpojo(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CANCELFRIENDREQUEST)
    Call<CancelFriendRequest> cancelrequestpojo (@Field("id") String id,
                                                 @Field("sender_id") String sender_id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.GETPROFILE)
    Call<GetProfile> getprofilepojo (@Field("index") String index,
                                     @Field("user_id") String user_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLEPOSTLIST)
    Call<CirclePostListPojo> circlepostlistpojocall(@Field("index") String index,
                                                    @Field("circle_id") String circle_id,
                                                    @Field("countries_id") String countries_id,
                                                    @Field("state_id") String state_id);

    @Multipart
    @POST(ServerUrl.CIRCLEPOSTLISTADD)
    Call<CirclePostAddPojo> circlepostaddpojocall(@Part("user_id") RequestBody user_id,
                                                  @Part("circle_id") RequestBody circle_id,
                                                  @Part("counties_id") RequestBody countries_id,
                                                  @Part("state_id") RequestBody state_id,
                                                  @Part("comments") RequestBody comments,
                                                  @Part MultipartBody.Part post_files);

    @Headers({"Content-Type: 3application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLEPOSTLISTDELET)
    Call<CirclePostDeleteP> circlepostdeletepojocall(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.LIKE)
    Call<Like> likepojo(@Field("user_id") String user_id,
                        @Field("post_id") String post_id);


    @Headers({"Content-Type: 3application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.UNLIKE)
    Call<Unlike> unlikepojo(@Field("user_id") String user_id,
                            @Field("post_id") String post_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CHATLIST)
    Call<ChatList> chatlistpojo(@Field("sender_id") String sender_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.COMMENTS)
    Call<Comment> commentpojo(@Field("user_id") String user_id,
                              @Field("post_id") String post_id,
                              @Field("comments") String comments);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.FOLLOWING)
    Call<Following> followingpojo(@Field("follower_id") String follower_id,
                                  @Field("following_id") String following_id);

}