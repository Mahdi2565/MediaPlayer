package ir.mahdidev.mediaplayer.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.mahdidev.mediaplayer.EventBusMusicMessage;
import ir.mahdidev.mediaplayer.model.MusicModel;

public class MusicPlayerController {
    public static MusicPlayerController musicPlayerController;
    private Context context;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int CurrentPosition = 0 ;
    private int musicPosition = 0 ;
    private int newPostion ;
    private EventBusMusicMessage eventBusMusicMessage ;
    private List<MusicModel> musicList = new ArrayList<>();
    private boolean isPause = false;
    private boolean isShuffle = false;
    private boolean isOneRepeat = false;
    private boolean isAllRepeat = true ;

    public static MusicPlayerController getInstance(Context context){
        if (musicPlayerController == null){
            musicPlayerController = new MusicPlayerController(context);
        }
        return musicPlayerController;
    }
    private MusicPlayerController(Context context){
        this.context = context;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public EventBusMusicMessage getEventBusMusicMessage() {
        return eventBusMusicMessage;
    }

    public void setEventBusMusicMessage(EventBusMusicMessage eventBusMusicMessage) {
        this.eventBusMusicMessage = eventBusMusicMessage;
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isShuffle() {
        return isShuffle;
    }

    public boolean isOneRepeat() {
        return isOneRepeat;
    }

    public boolean isAllRepeat() {
        return isAllRepeat;
    }

    public void playMusic(final EventBusMusicMessage eventBusMusicMessage){
        if (mediaPlayer==null) mediaPlayer = new MediaPlayer();
        if (isPause){
            isPause = false;
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
        }
        this.eventBusMusicMessage = eventBusMusicMessage;
        musicList.addAll(eventBusMusicMessage.getMusicModels());
        newPostion = eventBusMusicMessage.getPosition();

        Uri uri = PictureUtils.getTrackUri(eventBusMusicMessage.getMusicModel().getId());
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = new MediaPlayer();
        }

        try {
            mediaPlayer.setDataSource(context.getApplicationContext() , uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (isAllRepeat){
                    isOneRepeat = true;
                }
                nextMusic(mediaPlayer, eventBusMusicMessage );
                isOneRepeat = false ;
            }
        });
    }

    public void repeatMusic(){
        if (isOneRepeat){
            //mediaPlayer.setLooping(false);
            isOneRepeat = false;
        }else {
           // mediaPlayer.setLooping(true);
            isOneRepeat = true;
        }
    }

    public void shuffleMusic() {
        if (!isShuffle){
            newPostion = new Random().nextInt((musicList.size() - 1) + 1);
            isShuffle = true;
        }else {
            newPostion = eventBusMusicMessage.getPosition();
            isShuffle = false;
        }
    }



    public void previousMusic(MediaPlayer mediaPlayer, EventBusMusicMessage eventBusMusicMessage){
        newPostion = (--newPostion) % musicList.size();
        if (newPostion<0) newPostion=musicList.size()-1;
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(context , PictureUtils
                    .getTrackUri(musicList.get(newPostion).getId()));
            mediaPlayer.prepare();
            mediaPlayer.start();
            EventBus.getDefault().post(musicList.get(newPostion));
            setEventBusMusicMessage(new EventBusMusicMessage(musicList.get(newPostion) , newPostion ,
                    musicList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void nextMusic(MediaPlayer mediaPlayer, EventBusMusicMessage eventBusMusicMessage ) {
        if (!isOneRepeat){
            newPostion = (++newPostion) % musicList.size();
        }else newPostion = getEventBusMusicMessage().getPosition();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(context , PictureUtils
                        .getTrackUri(musicList.get(newPostion).getId()));
                mediaPlayer.prepare();
                mediaPlayer.start();
                EventBus.getDefault().post(musicList.get(newPostion));
                setEventBusMusicMessage(new EventBusMusicMessage(musicList.get(newPostion) , newPostion ,
                        musicList));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void resumeMusic(EventBusMusicMessage eventBusMusicMessage){
        if (this.getEventBusMusicMessage().getMusicModel().getId()== eventBusMusicMessage.getMusicModel().getId()){
            if (musicPosition>0) {
                mediaPlayer.seekTo(musicPosition);
                mediaPlayer.start();
                musicPosition = 0;
            }
        }
    }
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }
    public void pauseMusic() {
        mediaPlayer.pause();
        musicPosition = mediaPlayer.getCurrentPosition();
        isPause = true;
    }

}
