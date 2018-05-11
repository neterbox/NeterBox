package com.neterbox.retrofit;

import com.neterbox.jsonpojo.AddChat.AddChat;
import com.neterbox.jsonpojo.ChangePassword.ChangePassword;
import com.neterbox.jsonpojo.CirclePostadd.CirclePostAddPojo;
import com.neterbox.jsonpojo.Decline_friend_request.DeclineFriendRequest;
import com.neterbox.jsonpojo.Forgot_Password.ForgotPassword;
import com.neterbox.jsonpojo.Unfollow.Unfollow;
import com.neterbox.jsonpojo.addmember.AddMember;
import com.neterbox.jsonpojo.chatlist.ChatList;
import com.neterbox.jsonpojo.circle_chat.CircleChat;
import com.neterbox.jsonpojo.circlepost_list.CirclePostListPojo;
import com.neterbox.jsonpojo.comment.Comment;
import com.neterbox.jsonpojo.create_group.AddGroup;
import com.neterbox.jsonpojo.delete_group.DeleteGroup;
import com.neterbox.jsonpojo.deletechat.DeleteChat;
import com.neterbox.jsonpojo.edit_post.EditPost;
import com.neterbox.jsonpojo.followerlist.Followerlist;
import com.neterbox.jsonpojo.followingadd.Following;
import com.neterbox.jsonpojo.followinglist.Followinglist;
import com.neterbox.jsonpojo.friend_requestlist.FrndReqListModel;
import com.neterbox.jsonpojo.Login.Login;
import com.neterbox.jsonpojo.accept_friend_request.AcceptFriendRequest;
import com.neterbox.jsonpojo.cancel_friend_request.CancelFriendRequest;
import com.neterbox.jsonpojo.circle.Circlepage;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
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
import com.neterbox.jsonpojo.updateqb.UpdateQB;
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

    @Multipart
    @POST(ServerUrl.EDITPOST)
    Call<EditPost> editpostpojo(@Part("id") RequestBody id,
                                @Part("comments") RequestBody comments);


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

    @Multipart
    @POST(ServerUrl.CIRCLEPOSTLISTADD)
    Call<CirclePostAddPojo> circlepostaddpojocall(@Part("user_id") RequestBody user_id,
                                                  @Part("circle_id") RequestBody circle_id,
                                                  @Part("countries_id") RequestBody countries_id,
                                                  @Part("state_id") RequestBody state_id,
                                                  @Part("comments") RequestBody comments,
                                                  @Part("media_type") RequestBody media_type,
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
    Call<ChatList> chatlistpojo(@Field("sender_id") String sender_id,
                                @Field("index") String index);

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

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.UPDATEQBID)
    Call<UpdateQB> updateqbidpojo(@Field("sender_id") String sender_id,
                                 @Field("sender_qb_id") String sender_qb_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.FOLLOWERLIST)
    Call<Followerlist> followerlistpojo(@Field("follower_id") String follower_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.FOLLOWERLIST)
    Call<Followinglist> followinglistpojo(@Field("following_id") String following_id);


    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CHANGEPASSWORD)
    Call<ChangePassword> callApi_ChangePassword(@Field("id") String id, @Field("password") String password);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.FORGOTPASSWORD)
    Call<ForgotPassword> forgotPasswordCall(@Field("email") String email);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.UNFOLLOW)
    Call<Unfollow> unfollowpojo(@Field("id") String id);

    @Multipart
    @POST(ServerUrl.ADDGROUP)
    Call<AddGroup> addgrouppojo(@Part("user_id") RequestBody user_id,
                                @Part("members") RequestBody members,
                                @Part("sender_qb_id") RequestBody sender_qb_id,
                                @Part("receiver_qb_id") RequestBody receiver_qb_id,
                                @Part("dialog_id") RequestBody dialog_id,
                                @Part("dialog_type") RequestBody dialog_type,
                                @Part("dialog_status") Boolean dialog_status,
                                @Part("group_name") RequestBody group_name,
                                @Part MultipartBody.Part icon);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLEPOSTLIST)
    Call<CirclePostListPojo> circlepostlistpo(@Field("index") String index,
                                              @Field("circle_id") String circle_id,
                                              @Field("countries_id") String countries_id,
                                              @Field("state_id") String state_id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.DELETEGROUP)
    Call<DeleteGroup> deletegrouppojo(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.DELETECHAT)
    Call<DeleteChat> deletechatpojo(@Field("id") String id);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.CIRCLECHAT)
    Call<CircleChat> circlechatpojo(@Field("user_id") String user_id,
                                    @Field("circle_id") String circle_id,
                                    @Field("message") String message);
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    @POST(ServerUrl.ADDMEMBER   )
    Call<AddMember> addmemberpojo(@Field("id") String user_id,
                                  @Field("members") String circle_id);
}