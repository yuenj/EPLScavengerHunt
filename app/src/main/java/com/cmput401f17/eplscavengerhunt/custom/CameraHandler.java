package com.cmput401f17.eplscavengerhunt.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * CameraHandler has the task of Requesting the device's system Camera app for a photo
 * and handles modifications to the image the camera returns
 */
public class CameraHandler {
    private File imageFile;
    private Context context;

    public CameraHandler(Context context) {
        this.context = context;
    }

    /**
     * Sends intent to system camera for a photo if an imageFile file
     * has been successfully generated by createImageFile.
     * Taken from https://developer.android.com/training/camera/photobasics.html
     * accessed 10-24-2017
     *
     * @param requestImageCapture, the activity's constant for REQUEST_IMAGE_CAPTURE
     * @return imageFile instance.
     */
    public File dispatchTakePictureIntent(int requestImageCapture) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                imageFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (imageFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.cmput401f17.fileprovider",
                        imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                ((Activity) context).startActivityForResult(takePictureIntent, requestImageCapture);
            }
        }
        return imageFile;
    }

    /**
     * fullsize imageFile is downscaled in the bitmap
     * Note: actual imageFile remains untouched
     */
    public Bitmap downScaleBitMap(File imageFile) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        String absolutePath = imageFile.getAbsolutePath();
        return BitmapFactory.decodeFile(absolutePath, opts);
    }

    /**
     * Generates an imageFile instance which has an empty imageFile file.
     * Taken from https://developer.android.com/training/camera/photobasics.html
     * Accessed 10-24-2017
     */
    private File createImageFile() throws IOException {
        // Create an imageFile file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg",  /* suffix */
                storageDir     /* directory */
        );
        return imageFile;
    }
}
