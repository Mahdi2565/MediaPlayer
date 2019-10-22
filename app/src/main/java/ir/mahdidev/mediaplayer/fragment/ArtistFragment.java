package ir.mahdidev.mediaplayer.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mahdidev.mediaplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {


    public ArtistFragment() {
        // Required empty public constructor
    }

    public static ArtistFragment newInstance() {
        Bundle args = new Bundle();
        ArtistFragment fragment = new ArtistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

}
