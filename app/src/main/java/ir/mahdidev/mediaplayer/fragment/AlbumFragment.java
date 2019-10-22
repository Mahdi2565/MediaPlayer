package ir.mahdidev.mediaplayer.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.mahdidev.mediaplayer.R;
import ir.mahdidev.mediaplayer.adapter.AlbumRecyclerViewAdapter;
import ir.mahdidev.mediaplayer.model.AlbumModel;
import ir.mahdidev.mediaplayer.model.Repository;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {

    private Repository repository = Repository.getInstance();
    private List<AlbumModel> albumList ;
    private AlbumRecyclerViewAdapter albumRecyclerViewAdapter;
    private RecyclerView albumRecyclerView;

    public AlbumFragment() {
    }

    public static AlbumFragment newInstance() {
        Bundle args = new Bundle();
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumList = repository.getAlbumList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
    }

    private void initRecyclerView() {
        albumRecyclerViewAdapter = new AlbumRecyclerViewAdapter(getActivity() , albumList);
        albumRecyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 2));
        albumRecyclerView.setAdapter(albumRecyclerViewAdapter);
    }

    private void initViews(View v) {
        albumRecyclerView = v.findViewById(R.id.album_recyclerview);
    }
}
