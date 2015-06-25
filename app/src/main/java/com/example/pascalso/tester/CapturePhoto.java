package com.example.pascalso.tester;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.view.View;
/**
 * Created by owner on 6/25/15.
 */
public class CapturePhoto {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public CapturePhoto(){
        //onClick();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.takepic:{
                //dispatchTakePictureIntent();
                break;
            }
            case R.id.gallery:{

            }
            case R.id.received:{

            }
        }
    }

/**
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
 */

}
