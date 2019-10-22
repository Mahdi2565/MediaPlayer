package ir.mahdidev.mediaplayer.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.SongRecyclerViewAdapter;
import ir.mahdidev.mediaplayer.model.MusicModel;
import ir.mahdidev.mediaplayer.model.Repository;
import ir.mahdidev.mediaplayer.utils.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    private Repository repository = Repository.getInstance();
    private List<MusicModel> musicList = new ArrayList<>();
    private RecyclerView songRecyclerView;
    private SongRecyclerViewAdapter songRecyclerViewAdapter;

    public MusicFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPermission()){
            musicList = repository.getMusicList();
        }
    }

    public static MusicFragment newInstance() {
        Bundle args = new Bundle();
        MusicFragment fragment = new MusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        songRecyclerViewAdapter = new SongRecyclerViewAdapter(musicList , getActivity());
        songRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        songRecyclerView.setAdapter(songRecyclerViewAdapter);

    }

    private void initViews(View v) {
        songRecyclerView = v.findViewById(R.id.song_recyclerview);
    }


    private boolean checkPermission (){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M) return true;
        if (ActivityCompat.checkSelfPermission(getActivity() , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
            return false;
        }else return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == Const.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION){

            }
        }else checkPermission();
    }
}
