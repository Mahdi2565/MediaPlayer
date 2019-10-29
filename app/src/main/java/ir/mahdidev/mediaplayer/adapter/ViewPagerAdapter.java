package ir.mahdidev.mediaplayer.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import ir.mahdidev.mediaplayer.fragment.AlbumFragment;
import ir.mahdidev.mediaplayer.fragment.ArtistFragment;
import ir.mahdidev.mediaplayer.fragment.MusicFragment;
import ir.mahdidev.mediaplayer.model.AlbumModel;
import ir.mahdidev.mediaplayer.model.ArtistModel;
import ir.mahdidev.mediaplayer.model.MusicModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {

   private List<MusicModel> musicModels;
   private List<AlbumModel> albumModels;
   private List<ArtistModel> artistModels;
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<MusicModel> musicModels,
                            List<AlbumModel> albumModels, List<ArtistModel> artistModels) {
        super(fm, behavior);
        this.musicModels = musicModels;
        this.albumModels = albumModels;
        this.artistModels = artistModels;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return MusicFragment.newInstance(musicModels);
            case 1 : return AlbumFragment.newInstance(albumModels);
            case 2 : return ArtistFragment.newInstance(artistModels);
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Songs";
            case 1: return "Albums";
            case 2: return "Artists";
        }
        return super.getPageTitle(position);
    }

}
