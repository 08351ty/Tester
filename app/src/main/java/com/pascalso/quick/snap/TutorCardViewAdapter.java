package com.pascalso.quick.snap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owner on 8/30/15.
 */
public class TutorCardViewAdapter extends RecyclerView.Adapter<TutorCardViewAdapter.ViewHolder>{
    List<TutorProfile> mProfiles;
    List<TutorProfile> brown;
    List<TutorProfile> chicago;
    List<TutorProfile> emory;
    List<TutorProfile> swarthmore;
    private String college;


    public TutorCardViewAdapter(){
        super();
        college = UniversityList.getCollege();
        switch(college){
            case "brown":
                brown = new ArrayList<>();
                TutorProfile brownprofile = new TutorProfile();
                brownprofile.setName("Claudia");
                brownprofile.setDes("Bachelor of Arts");
                brownprofile.setThumbnail(R.drawable.cnheadshot);
                brown.add(brownprofile);
                break;
            case "chicago":
                chicago = new ArrayList<>();
                TutorProfile chicagoprofile = new TutorProfile();
                chicagoprofile.setName("Caleb");
                chicagoprofile.setDes("Bachelor of Arts in Political Science");
                chicagoprofile.setThumbnail(R.drawable.cbheadshot);
                chicago.add(chicagoprofile);

                chicagoprofile = new TutorProfile();
                chicagoprofile.setName("Joshua");
                chicagoprofile.setDes("Bachelor of Arts in Economics and Public Policy");
                chicagoprofile.setThumbnail(R.drawable.jlheadshot);
                chicago.add(chicagoprofile);

                chicagoprofile = new TutorProfile();
                chicagoprofile.setName("Pascal");
                chicagoprofile.setDes("Bachelor of Arts in Economics and Computer Science");
                chicagoprofile.setThumbnail(R.drawable.psheadshot);
                chicago.add(chicagoprofile);
                break;
            case "emory":
                emory = new ArrayList<>();
                TutorProfile emoryprofile = new TutorProfile();
                emoryprofile.setName("Wesley");
                emoryprofile.setDes("Bachelor of Arts in Economics");
                emoryprofile.setThumbnail(R.drawable.wtheadshot);
                emory.add(emoryprofile);
                break;
            case "swarthmore":
                swarthmore = new ArrayList<>();
                TutorProfile swarthmoreprofile = new TutorProfile();
                swarthmoreprofile.setName("Natasha");
                swarthmoreprofile.setDes("Bachelor of Arts in Political Science");
                swarthmoreprofile.setThumbnail(R.drawable.ncheadshot);
                swarthmore.add(swarthmoreprofile);
                break;
        }

        /**
        mProfiles = new ArrayList<>();
        TutorProfile profile = new TutorProfile();
        profile.setName("Wesley Tang");
        profile.setDes("Bachelor of Arts in Economics");
        profile.setThumbnail(R.drawable.wtheadshot);
        mProfiles.add(profile);

        profile = new TutorProfile();
        profile.setName("Pascal So");
        profile.setDes("Bachelor of Arts in Computer Science");
        profile.setThumbnail(R.drawable.psheadshot);
        mProfiles.add(profile);
         */
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_cardview, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        TutorProfile profile = null;
        switch(college){
            case "brown":
                profile = brown.get(i);
                break;
            case "chicago":
                profile = chicago.get(i);
                break;
            case "emory":
                profile = emory.get(i);
                break;
            case "swarthmore":
                profile = swarthmore.get(i);
        }
        viewHolder.tvName.setText(profile.getName());
        viewHolder.tvDes.setText(profile.getDes());
        viewHolder.imgThumbnail.setImageResource(profile.getThumbnail());

    }

    @Override
    public int getItemCount(){
        int x = 0;
        switch(college){
            case "brown":
                x = brown.size();
                break;
            case "chicago":
                x = chicago.size();
                break;
            case "emory":
                x = emory.size();
                break;
            case "swarthmore":
                x = swarthmore.size();
                break;
        }
        return x;

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgThumbnail;
        public TextView tvName;
        public TextView tvDes;

        public ViewHolder(View itemView){
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.headshot1);
            tvName = (TextView)itemView.findViewById(R.id.name1);
            tvDes = (TextView)itemView.findViewById(R.id.description1);
        }
    }
}
