package com.pascalso.quick.snap;

import android.app.Activity;

/**
 * Created by Pascal So on 7/20/2015.
 */
public class CardView extends Activity {
/**
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardview);
        TextView captionTitle1 = (TextView) findViewById(R.id.name1);
        ImageView imageView1 = (ImageView) findViewById(R.id.headshot1);
        TextView captionTitle2 = (TextView) findViewById(R.id.physicstext);
        ImageView imageView2 = (ImageView) findViewById(R.id.physics);


        captionTitle1.setTextColor(Color.WHITE);
        captionTitle2.setTextColor(Color.WHITE);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_math);
        int gradientStartColor = Color.argb(0, 0, 0, 0);
        int gradientEndColor = Color.argb(255, 0, 0, 0);
        GradientOverImageDrawable gradientDrawable = new GradientOverImageDrawable(getResources(), image);
        gradientDrawable.setGradientColors(gradientStartColor, gradientEndColor);

        imageView1.setImageDrawable(gradientDrawable);
        imageView2.setImageDrawable(gradientDrawable);
    }
 */
}
