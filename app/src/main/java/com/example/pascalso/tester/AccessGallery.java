package com.example.pascalso.tester;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by owner on 6/26/15.
 */
public class AccessGallery {
/**
    private int REQUEST_GALLERY = 2;

    public AccessGallery(){
        dispatchGalleryIntent();
    }

    public void galleryClick(){
        ImageButton gallery = (ImageButton)findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                dispatchGalleryIntent();
            }
        });
    }

    public void dispatchGalleryIntent(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(galleryIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }
 */
}
