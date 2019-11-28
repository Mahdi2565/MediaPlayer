package ir.mahdidev.mediaplayer.fragment;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ir.mahdidev.mediaplayer.EventBusMusicMessage;
import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.ViewPagerAdapter;
import ir.mahdidev.mediaplayer.model.AlbumModel;
import ir.mahdidev.mediaplayer.model.ArtistModel;
import ir.mahdidev.mediaplayer.model.MusicModel;
import ir.mahdidev.mediaplayer.model.Repository;
import ir.mahdidev.mediaplayer.utils.Const;
import ir.mahdidev.mediaplayer.utils.MusicPlayerController;
import ir.mahdidev.mediaplayer.utils.PictureUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private SlidingUpPanelLayout slidingLayout;
    private ImageView imageMusic;
    private TextView  titleMusic;
    private TextView  artistMusic;
    private ImageView playPauseMusic;
    private ProgressBar progressBar;
    private MusicPlayerController controller ;
    private ImageView fullCoverImage;
    private RelativeLayout draggedView;
    private ImageView playPauseController;
    private ImageView nextMusic;
    private ImageView previousMusic;
    private ImageView shuffleMusic;
    private ImageView repeatMusic;
    private AppCompatSeekBar seekBar;
    private TextView musicSpendTime;
    private TextView musicDuration;
    private Handler mHandler = new Handler();
    private Runnable runnable;
    private static EventBusMusicMessage eventBusMusicMessage;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = MusicPlayerController.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (!checkPermission())return;

            getCurrentMusic();
            getMusicInformation();
            playPauseFunction();
            nextPreviousFunction();
            shuffleMusicFunction();
            repeatMusicFunction();
    }

    private void repeatMusicFunction() {
        repeatMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.isAllRepeat()){
                    DrawableCompat.setTint(repeatMusic.getDrawable(), ContextCompat.getColor(getActivity(), R.color.white));
                }else {
                    DrawableCompat.setTint(repeatMusic.getDrawable(), ContextCompat.getColor(getActivity(), R.color.orange));
                }
                controller.repeatMusic();
            }
        });
    }

    private void shuffleMusicFunction() {
        shuffleMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.isShuffle()){
                    DrawableCompat.setTint(shuffleMusic.getDrawable(), ContextCompat.getColor(getActivity(), R.color.white));
                }else {
                    DrawableCompat.setTint(shuffleMusic.getDrawable(), ContextCompat.getColor(getActivity(), R.color.orange));
                }
                controller.shuffleMusic();
            }
        });
    }

    private void nextPreviousFunction() {
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.nextMusic(controller.getMediaPlayer() , eventBusMusicMessage );
            }
        });
        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.previousMusic(controller.getMediaPlayer() , eventBusMusicMessage );

            }
        });
    }

    private void playPauseFunction() {
        playPauseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.isPlaying()){
                    controller.pauseMusic();
                    playPauseMusic.setImageResource(R.drawable.ic_play_icon);
                    playPauseController.setImageResource(R.drawable.ic_play_rounded_icon);
                }else{
                    playPauseMusic.setImageResource(R.drawable.ic_pause_icon);
                    playPauseController.setImageResource(R.drawable.ic_pause_rounded_icon);
                    controller.resumeMusic(eventBusMusicMessage);
                }
            }
        });
        playPauseController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.getMediaPlayer() != null) {
                    if (controller.isPlaying()) {
                        controller.pauseMusic();
                        playPauseController.setImageResource(R.drawable.ic_play_rounded_icon);
                        playPauseMusic.setImageResource(R.drawable.ic_play_icon);
                    } else {
                        playPauseController.setImageResource(R.drawable.ic_pause_rounded_icon);
                        playPauseMusic.setImageResource(R.drawable.ic_pause_icon);
                        controller.resumeMusic(eventBusMusicMessage);
                    }
                }else {
                    controller.playMusic(eventBusMusicMessage);
                }
            }
        });

    }

    private void getCurrentMusic() {
        if (controller.isPlaying()|| controller.isPause()){
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            setMusicToView(controller.getEventBusMusicMessage().getMusicModel());
            initSeekbar(controller.getMediaPlayer());
        }else {
            playPauseMusic.setImageResource(R.drawable.ic_play_icon);
            playPauseController.setImageResource(R.drawable.ic_play_rounded_icon);
        }
        if (controller.isPause()) {
            playPauseMusic.setImageResource(R.drawable.ic_play_icon);
            playPauseController.setImageResource(R.drawable.ic_play_rounded_icon);
        }
    }

    private void getMusicInformation() {
        progressBar.setVisibility(View.VISIBLE);
        AsyncTask asyncTask = new AsyncTask();
        asyncTask.execute();
    }

    private void initViewPager(List<MusicModel> musicModels , List<AlbumModel> albumModels ,
                               List<ArtistModel> artistModels) {
        if (checkPermission()){
            viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager() , 0
            , musicModels , albumModels , artistModels);
            tabLayout.setupWithViewPager(viewPager);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOffscreenPageLimit(3);
        }else checkPermission();
    }

    private void initViews(View v) {
        fullCoverImage = v.findViewById(R.id.full_cover_image);
        progressBar = v.findViewById(R.id.progress_bar);
        viewPager = v.findViewById(R.id.viewPager);
        tabLayout = v.findViewById(R.id.tab_Layout);
        slidingLayout = v.findViewById(R.id.sliding_layout);
        imageMusic    = v.findViewById(R.id.cover_music);
        titleMusic    = v.findViewById(R.id.title_music);
        artistMusic   = v.findViewById(R.id.artist_music);
        playPauseMusic = v.findViewById(R.id.play_pause_btn_music);
        draggedView    = v.findViewById(R.id.draggedView);
        playPauseController = v.findViewById(R.id.play_pause_controller);
        seekBar             = v.findViewById(R.id.seek_bar);
        musicSpendTime      = v.findViewById(R.id.music_spend_time);
        musicDuration       = v.findViewById(R.id.music_duration);
        nextMusic           = v.findViewById(R.id.next_music);
        previousMusic       = v.findViewById(R.id.previous_music);
        shuffleMusic        = v.findViewById(R.id.shuffle_music);
        repeatMusic         = v.findViewById(R.id.repeat_music);
        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                if (slideOffset==1){
                    draggedView.setAlpha(0.9F);
                    playPauseMusic.setVisibility(View.GONE);
                }else {
                    draggedView.setAlpha(1F);
                    playPauseMusic.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            }
        });
    }

    private boolean checkPermission (){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return true;
        if (ContextCompat.checkSelfPermission(getContext() , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
            return false;
        }else return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case  Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION :{

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMusicInformation();

                }else checkPermission();
                break;
            }
        }
    }
    @Subscribe
    public void onMusicReceived(EventBusMusicMessage eventBusMusicMessage){
        slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        this.eventBusMusicMessage = eventBusMusicMessage;
        setMusicToView(eventBusMusicMessage.getMusicModel());
        if (controller.isPlaying()){
            if (eventBusMusicMessage.getMusicModel().getId()== controller.getEventBusMusicMessage().getMusicModel().getId()){
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }else {
                controller.playMusic(eventBusMusicMessage);
            }
        }else controller.playMusic(eventBusMusicMessage);
        initSeekbar(controller.getMediaPlayer());
    }

    @Subscribe
    public void nextMusicInformation(MusicModel musicModel){
        setMusicToView(musicModel);
        initSeekbar(controller.getMediaPlayer());
    }

    private void initSeekbar(final MediaPlayer mediaPlayer) {
         int duration  = controller.getMediaPlayer().getDuration()/1000;
        seekBar.setMax(duration);
        runnable = new Runnable() {
            @Override
            public void run() {
                if(controller.getMediaPlayer() != null){
                    try {
                        int due = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition());
                        int pass = mediaPlayer.getDuration() - (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition());
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                        musicDuration.setText(getMusicState(due));
                        musicSpendTime.setText(getMusicState(pass));
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }

                }else{
                    seekBar.setProgress(0);
                    playPauseController.setImageResource(R.drawable.ic_play_rounded_icon);
                    playPauseMusic.setImageResource(R.drawable.ic_play_icon);
                    mHandler.removeCallbacksAndMessages(null);
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        getActivity().runOnUiThread(runnable);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(controller.getMediaPlayer() != null && b){
                    controller.getMediaPlayer().seekTo(i * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private String getMusicState(int time) {
        return String.format(Locale.ENGLISH , "%2d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(time),
        TimeUnit.MILLISECONDS.toSeconds(time) -
        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));
    }

    private void setMusicToView(MusicModel musicModel) {
        titleMusic.setText(musicModel.getSongName());
        artistMusic.setText(musicModel.getArtist());
        imageMusic.setImageBitmap(PictureUtils.getCoverImage(musicModel.getId() , getContext()));
        fullCoverImage.setImageBitmap(PictureUtils.getCoverImage(musicModel.getId() , getContext()));
        playPauseMusic.setImageResource(R.drawable.ic_pause_icon);
        playPauseController.setImageResource(R.drawable.ic_pause_rounded_icon);
        Log.e("TAG4" , "HERE");
    }


    public class AsyncTask extends android.os.AsyncTask<Void, Void , List<List>>{

        @Override
        protected List<List> doInBackground(Void... voids) {
            Repository repository = Repository.getInstance();
            List<MusicModel> musicModelList = repository.getMusicList();
            List<AlbumModel> albumList = repository.getAlbumList();
            List<ArtistModel> artistList = repository.getArtistList();
            List<List> lists = new ArrayList<>();
            lists.add(musicModelList);
            lists.add(albumList);
            lists.add(artistList);
            return lists;
        }

        @Override
        protected void onPostExecute(List<List> lists) {
            super.onPostExecute(lists);
            List<MusicModel> musicModelList = (List<MusicModel>) lists.get(0);
            List<AlbumModel> albumList = (List<AlbumModel>) lists.get(1);
            List<ArtistModel> artistList = (List<ArtistModel>) lists.get(2);
            initViewPager(musicModelList , albumList , artistList);
            progressBar.setVisibility(View.GONE);
        }
    }
}
