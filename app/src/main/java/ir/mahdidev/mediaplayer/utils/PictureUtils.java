package ir.mahdidev.mediaplayer.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.model.MusicModel;
import ir.mahdidev.mediaplayer.model.Repository;

public class PictureUtils {
    public static Bitmap getscalledBitmap(byte[] imageByte , int width , int height){

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        //just get data of bitmap
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imageByte ,0 , imageByte.length , bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/width, photoH/height);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeByteArray(imageByte ,0 , imageByte.length , bmOptions);

    }

    public static Bitmap getScaledBitmap(byte[] imageByte, Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return getscalledBitmap(imageByte, point.x/4, point.y/4);
    }

    public static Bitmap getCoverImage(int id , Context context) {
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        mediaMetadata.setDataSource(Global.getContext().getApplicationContext(), getTrackUri(id));
        byte[] imageByte = mediaMetadata.getEmbeddedPicture();
        if (imageByte != null)
            return PictureUtils.getScaledBitmap(imageByte , (Activity) context);
        else return BitmapFactory.decodeResource(context.getResources() , R.drawable.music_place_holder);
    }
    public static Bitmap getCoverImage(String albumKey , Repository repository , Context context){
        List<MusicModel> musicList = repository.getMusicList(albumKey);
        MediaMetadataRetriever mediaMetadata = new MediaMetadataRetriever();
        mediaMetadata.setDataSource(Global.getContext().getApplicationContext() ,
                getTrackUri(musicList.get(0).getId()));
        byte [] imageByte = mediaMetadata.getEmbeddedPicture();
        if (imageByte !=null)
            return PictureUtils.getScaledBitmap(imageByte , (Activity) context);
        else return BitmapFactory.decodeResource(context.getResources() , R.drawable.music_place_holder);

    }

    public static Uri getTrackUri(int id) {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
    }
}
