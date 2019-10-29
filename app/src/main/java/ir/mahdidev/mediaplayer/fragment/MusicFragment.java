package ir.mahdidev.mediaplayer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
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

    private List<MusicModel> musicList = new ArrayList<>();
    private RecyclerView songRecyclerView;
    private SongRecyclerViewAdapter songRecyclerViewAdapter;

    public MusicFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null ){
            musicList = (List<MusicModel>) getArguments().getSerializable(Const.MUSIC_LIST_BUNDLE);
        }
    }

    public static MusicFragment newInstance(List<MusicModel> musicModels) {
        Bundle args = new Bundle();
        args.putSerializable(Const.MUSIC_LIST_BUNDLE , (Serializable) musicModels);
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


}
