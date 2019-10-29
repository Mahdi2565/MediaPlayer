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
import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.ArtistRecyclerViewAdapter;
import ir.mahdidev.mediaplayer.model.ArtistModel;
import ir.mahdidev.mediaplayer.model.Repository;
import ir.mahdidev.mediaplayer.utils.Const;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    private RecyclerView artistRecyclerView;
    private ArtistRecyclerViewAdapter artistRecyclerViewAdapter;
    private List<ArtistModel> artistList;
    public ArtistFragment() {
    }

    public static ArtistFragment newInstance(List<ArtistModel> artistModels) {
        Bundle args = new Bundle();
        args.putSerializable(Const.ARTIST_LIST_BUNDLE , (Serializable) artistModels);
        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() !=null){
            artistList = (List<ArtistModel>) getArguments().getSerializable(Const.ARTIST_LIST_BUNDLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        artistRecyclerViewAdapter = new ArtistRecyclerViewAdapter(artistList , getActivity());
        artistRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        artistRecyclerView.setAdapter(artistRecyclerViewAdapter);
    }

    private void initViews(View v) {
        artistRecyclerView = v.findViewById(R.id.artist_recyclerview);
    }
}
