package com.pascalso.quick.snap;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by owner on 9/22/15.
 */
public class ChatListAdapter extends ArrayAdapter<Message> {
    private String mUserID;

    public ChatListAdapter(Context context, String userID, List<Message> messages){
        super(context, 0, messages);
        this.mUserID = userID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_chatitem, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageLeft = (ImageView)convertView.findViewById(R.id.ivProfileLeft);
            holder.imageRight = (ImageView)convertView.findViewById(R.id.ivProfileRight);
            holder.body = (TextView)convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);

        }
        final Message message = (Message)getItem(position);
        final ViewHolder holder = (ViewHolder)convertView.getTag();
        final boolean isMe = message.getUserId().equals(mUserID);

        if(isMe){
            holder.imageLeft.setVisibility(View.GONE);
            holder.imageRight.setVisibility(View.VISIBLE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        }
        else{
            holder.imageLeft.setVisibility(View.VISIBLE);
            holder.imageRight.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        }

        //Checks if tutor
        if(ChatActivity.getIdentity()){
            holder.imageLeft.setImageResource(R.drawable.ic_action_profile);
            holder.imageRight.setImageResource(R.drawable.ic_action_tutorprofile);
        }
        holder.body.setText(message.getBody());
        /**
        final ImageView profileView = isMe ? holder.imageRight : holder.imageLeft;
        Picasso.with(getContext())
                .load(getProfileUrl(message.getUserId()))
                .error(R.drawable.ic_action_profile)
                .into(profileView);
        holder.body.setText(message.getBody());
         */


        return convertView;
    }

    /**
    private static String getProfileUrl(final String userID){
        String hex = null;
        try{
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(userID.getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }
     */

    final class ViewHolder {
        public ImageView imageLeft;
        public ImageView imageRight;
        public TextView body;
    }
}
