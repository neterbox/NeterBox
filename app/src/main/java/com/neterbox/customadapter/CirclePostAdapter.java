package com.neterbox.customadapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.neterbox.CirclePost;
import com.neterbox.R;
import com.neterbox.jsonpojo.circlepost_list.CirclePostListDatum;
import com.neterbox.jsonpojo.circlepost_list.CirclePostListPojo;
import com.neterbox.jsonpojo.circlepostdelete.CirclePostDeleteP;
import com.neterbox.jsonpojo.edit_post.EditPost;
import com.neterbox.retrofit.APIClient;
import com.neterbox.retrofit.APIInterface;
import com.neterbox.utils.Constants;
import com.neterbox.utils.Sessionmanager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.neterbox.R.id.icircle_post_video;

/**
 * Created by DeLL on 27-04-2018.
 */

public class CirclePostAdapter extends BaseAdapter {
    Activity activity;
    private LayoutInflater inflater;
    public Resources res;
    Sessionmanager sessionmanager;
    List<CirclePostListDatum> circlePostListData;
    ImageView icircle_post_pic;
    String index = "1", circle_id, user_id, post_id, follower_id, circle_post, circle_follower_id;
    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
    int counter = 0;
    viewclick viewclicks;

    public CirclePostAdapter(Activity activity, List<CirclePostListDatum> circlePostListData) {

        this.activity = activity;
        this.circlePostListData = circlePostListData;
        sessionmanager = new Sessionmanager(activity);
        inflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return circlePostListData.size();
    }

    @Override
    public Object getItem(int position) {
        return circlePostListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final MyViewHolder viewHolder;

        circle_id = sessionmanager.getValue(Sessionmanager.CircleId);
        user_id = sessionmanager.getValue(Sessionmanager.Id);
        follower_id = circlePostListData.get(position).getUser().getId();
//        final String comments = circlePostListData.get(i).getComment().get(i).getC

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.circlepostitem, viewGroup, false);
            viewHolder = new MyViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }

        if (Constants.followerlistData != null && Constants.followerlistData.size() != 0) {
            for (int fl = 0; fl < Constants.followerlistData.size(); fl++) {
                if (Constants.followerlistData.get(fl).getFollowingDetail().getId().contains(follower_id)) {
                    viewHolder.tfollowing.setTextColor(Color.RED);
                    viewHolder.tfollowing.setText("Unfollow");
                } else {
                    viewHolder.tfollowing.setTextColor(activity.getResources().getColor(R.color.header_color));
                    viewHolder.tfollowing.setText("Following");
                }
            }
        }

        viewHolder.tcircle_post_name.setText(circlePostListData.get(position).getUser().getName());
        viewHolder.tcircle_post_cap.setText(circlePostListData.get(position).getPost().getComments());

        if (circlePostListData.get(position).getIsLike()) {
            viewHolder.tcircle_post_likesno.setText(circlePostListData.get(position).getPostlike());
            viewHolder.tcircle_post_like.setText("Unlike");
        } else {
            viewHolder.tcircle_post_likesno.setText(circlePostListData.get(position).getPostlike());
            viewHolder.tcircle_post_like.setText("Likes");

        }

//        if (Constants.followerlistData.size() != 0 && Constants.followerlistData != null) {
//            for (int fl = 0; fl < Constants.followerlistData.size(); fl++) {
//                if (Constants.followerlistData.get(fl).getFollowingDetail().getId().contains(user_id)) {
//                    viewHolder.tfollowing.setEnabled(false);
//                } else {
//                    viewHolder.tfollowing.setEnabled(true);
//                }
//            }
//        }


        if (circlePostListData.get(position).getPostFile() != null) {
            Glide.with(activity).load(circlePostListData.get(position).getUser().getProfilePic()).placeholder(R.drawable.dummy).into(viewHolder.cvcircle_post_profile);
            String datetime = circlePostListData.get(position).getPostFile().get(0).getCreated();
            String[] separated = datetime.split(" ");
            String date = separated[0];
            String time = separated[1];
            viewHolder.tcircle_post_time.setText(time);
        }


        if (circlePostListData != null) {
            if (circlePostListData.get(position).getPostlike() != null) {
                viewHolder.tcircle_post_likesno.setText(circlePostListData.get(position).getPostlike());
            }
        }

        if (!circlePostListData.get(position).getPostFile().equals("")) {
                if (circlePostListData.get(position).getPostFile().get(0).getMediaType().equalsIgnoreCase("image")) {
                    viewHolder.icircle_post_pic.setVisibility(View.VISIBLE);
                    viewHolder.icircle_post_video.setVisibility(View.GONE);
                    Glide.with(activity).load(circlePostListData.get(position).getPostFile().get(0).getFiles()).placeholder(R.drawable.dummy).into(viewHolder.icircle_post_pic);
                }
                else
                {
                    viewHolder.icircle_post_video.setVisibility(View.VISIBLE);
                    viewHolder.icircle_post_pic.setVisibility(View.GONE);
//                    MediaController mediaController= new MediaController(activity);
//                    mediaController.setAnchorView(viewHolder.icircle_post_video);

                    Uri uri=Uri.parse(circlePostListData.get(position).getPostFile().get(0).getFiles());
                    viewHolder.icircle_post_video.setVideoURI(uri);
                    viewHolder.icircle_post_video.setMediaController(new MediaController(activity));
                    viewHolder.icircle_post_video.start();
                }
            }


        viewHolder.icircle_post_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewclicks != null) {
                    viewclicks.OnClicked(position, "cPostPhoto");
                }
            }
        });

        viewHolder.lcircle_post_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(viewclicks != null)
               {
                   viewclicks.OnClicked(position,"cPostchannel");
               }
            }
        });

        viewHolder.lcircle_post_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewclicks != null) {
                    String likeStr = viewHolder.tcircle_post_like.getText().toString();
                    if (likeStr.equalsIgnoreCase("Likes")) {
                        if (!circlePostListData.get(position).getPostlike().equals("") && circlePostListData.get(position).getPostlike() != null) {
                            counter = Integer.valueOf(1 + circlePostListData.get(position).getPostlike());
                        } else {
                            counter = counter + 1;
                        }
                        viewHolder.tcircle_post_likesno.setText(circlePostListData.get(position).getPostlike());
                        viewHolder.tcircle_post_like.setText("Unlike");
                        viewHolder.tcircle_post_likesno.setText(String.valueOf(counter));
                        viewclicks.OnClicked(position,"pLikes");
                    } else {
                        if (!circlePostListData.get(position).getPostlike().equals("") && circlePostListData.get(position).getPostlike() != null) {
                            counter = Integer.valueOf(circlePostListData.get(position).getPostlike()) - 1;
                        } else {
                            counter = counter - 1;
                        }
                        counter = Integer.valueOf(circlePostListData.get(position).getPostlike()) - 1;
                        viewHolder.tcircle_post_likesno.setText(circlePostListData.get(position).getPostlike());
                        viewHolder.tcircle_post_like.setText("Likes");
                        viewHolder.tcircle_post_likesno.setText(String.valueOf(counter));
                        viewclicks.OnClicked(position,"pUnLikes");
                    }

                }

            }
        });

        viewHolder.lcircle_post_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(viewclicks!= null)
              {
                  viewclicks.OnClicked(position,"pComments");
              }
            }
        });

        viewHolder.lfollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewclicks!=null) {
                    String followerstr = viewHolder.tfollowing.getText().toString();
                    if (followerstr.equalsIgnoreCase("Following")) {
                        viewHolder.tfollowing.setTextColor(Color.RED);
                        viewHolder.tfollowing.setText("Unfollow");
                        viewclicks.OnClicked(position,"pfollow");
                    } else {
                        viewHolder.tfollowing.setTextColor(activity.getResources().getColor(R.color.header_color));
                        viewHolder.tfollowing.setText("Following");
                        viewclicks.OnClicked(position,"pUnfollow");
                    }
                }
            }
        });

        viewHolder.dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(activity, v);
                popup.getMenuInflater().inflate(R.menu.poupup_circle_post, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_post:
                                CirclePostDelete(circlePostListData.get(position).getPost().getId());
                                break;
                            case R.id.edit_post:
                                popup(position);
                                Toast.makeText(activity, "edit Post", Toast.LENGTH_SHORT).show();
                                break;
                        }

                        return true;
                    }
                });

                popup.show(); //showing popup menu

            }
        });

        return convertView;

    }

    static class MyViewHolder {
        CircleImageView cvcircle_post_profile;
        TextView tcircle_post_name, tfollowing, tcircle_post_time, tcircle_post_cap, tcircle_post_commentno, tcircle_post_likesno, tcircle_post_like;
        ImageView icircle_post_pic, dot;
        VideoView icircle_post_video;
        LinearLayout lcircle_post_comment, lcircle_post_likes, lfollowing, lcircle_post_channel;

        public MyViewHolder(View item) {
            tcircle_post_name = (TextView) item.findViewById(R.id.tcircle_post_name);
            tfollowing = (TextView) item.findViewById(R.id.tfollowing);
            tcircle_post_like = (TextView) item.findViewById(R.id.tcircle_post_like);
            tcircle_post_time = (TextView) item.findViewById(R.id.tcircle_post_time);
            tcircle_post_cap = (TextView) item.findViewById(R.id.tcircle_post_cap);
            tcircle_post_commentno = (TextView) item.findViewById(R.id.tcircle_post_commentno);
            tcircle_post_likesno = (TextView) item.findViewById(R.id.tcircle_post_likesno);
            icircle_post_video = (VideoView) item.findViewById(R.id.icircle_post_video);
            icircle_post_pic = (ImageView) item.findViewById(R.id.icircle_post_pic);
            dot = (ImageView) item.findViewById(R.id.dot);
            lcircle_post_comment = (LinearLayout) item.findViewById(R.id.lcircle_post_comment);
            lcircle_post_likes = (LinearLayout) item.findViewById(R.id.lcircle_post_likes);
            lfollowing = (LinearLayout) item.findViewById(R.id.lfollowing);
            lcircle_post_channel = (LinearLayout) item.findViewById(R.id.lcircle_post_channel);
            cvcircle_post_profile = (CircleImageView) item.findViewById(R.id.cvcircle_post_profile);
        }
    }

    public void popup(final int position) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.circlepostcaption);

        icircle_post_pic = (ImageView) dialog.findViewById(R.id.icircle_post_pic);
        ImageView button_chat_send = (ImageView) dialog.findViewById(R.id.button_caption_send);
        final EditText edit_caption = (EditText) dialog.findViewById(R.id.edit_caption);
        circle_post = circlePostListData.get(position).getPostFile().get(0).getFiles();

        Glide.with(activity).load(circlePostListData.get(position).getPostFile().get(0).getFiles()).into(icircle_post_pic);
        edit_caption.setText(circlePostListData.get(position).getPost().getComments());
        dialog.show();

        button_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_post(circle_post, edit_caption.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void CirclePostDelete(String id) {

        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        Call<CirclePostDeleteP> registration = apiInterface.circlepostdeletepojocall(id);
        registration.enqueue(new Callback<CirclePostDeleteP>() {
            @Override
            public void onResponse(Call<CirclePostDeleteP> registrationCall, Response<CirclePostDeleteP> response) {

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    CirclePostPage(index, circle_id, CirclePost.countries_id, CirclePost.state_id);
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CirclePostDeleteP> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //    /*TODO CIrcle POSTLIST  API*/

    public void CirclePostPage(final String index, String circle_id, String countries_id, String state_id) {

        final Call<CirclePostListPojo> circlePostCall = apiInterface.circlepostlistpo(index, circle_id, countries_id, state_id);
        circlePostCall.enqueue(new Callback<CirclePostListPojo>() {
            @Override
            public void onResponse(Call<CirclePostListPojo> call, Response<CirclePostListPojo> response) {
                if (response.body().getStatus().equals("Success")) {
                    if (circlePostListData != null) {
                        circlePostListData.clear();
                    }
                    circlePostListData.addAll(response.body().getData());
                    notifyDataSetChanged();
//                    circlePostListData = response.body().getData();
                } else {
                }
            }

            @Override
            public void onFailure(Call<CirclePostListPojo> call, Throwable t) {
            }
        });
    }

    // TODO : CIRCLE EDIT POSTLIST
    public void edit_post(String post_id, String comments) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), post_id);
        RequestBody comment = RequestBody.create(MediaType.parse("text/plain"), comments);


        Call<EditPost> circlePostAddPCall = apiInterface.editpostpojo(id, comment);
        circlePostAddPCall.enqueue(new Callback<EditPost>() {
            @Override
            public void onResponse(Call<EditPost> registrationCall, Response<EditPost> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    CirclePostPage(index, circle_id, CirclePost.countries_id, CirclePost.state_id);
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EditPost> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public interface viewclick {
        void OnClicked(Integer position, String cPostPhoto);
    }

    public void setviewOnclick(viewclick viewclick) {
        this.viewclicks = viewclick;
    }
}
